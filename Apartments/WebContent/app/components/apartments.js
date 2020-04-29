Vue.component("view-apartments",{
    data() {
        return {
            apartments: [],
            searchData: {
                location: "",
                checkIn: "",
                checkOut: "",
                price: 0.0,
                rooms: 0,
                maxGuests:0
            }
        }
    },

    template:`
    <div id = "styleForApartmentsView">


        <form @submit="searchParam" method='post'>

            <input type="text" v-model="searchData.location" placeholder="Location..." >
            <input type="date" v-model="searchData.checkIn" placeholder="Check in...">
            <input type="date" v-model="searchData.checkOut" placeholder="Check out...">
            <input type="number" v-model="searchData.price" placeholder="Price per night..." >
            <input type="number" v-model="searchData.rooms" placeholder="Number of rooms ..." >
            <input type="number" v-model="searchData.maxGuests" placeholder="Max guests in room..." >

            <button type="submit" >Search</button>
            <button type="submit" @click="cancelSearch">Cancel search</button>

        </form>
        <br>


        <ul>
            <li v-for="apartment in apartments">
                <h2> {{ apartment.typeOfApartment }} </h2>
                <h2> {{ apartment.pricePerNight}} </h2>
            </li>
        </ul>
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
            <th> Status </th><th> Type </th><th> Price </th><th> Rooms </th><th> Guests</th><th> Check in</th><th> Check out</th><th>Location</th> </tr>
            <tr v-for="apartment in apartments">
                <td> {{ apartment.status }} </td>
                <td> {{ apartment.typeOfApartment }} </td>
                <td> {{ apartment.pricePerNight}} </td>
                <td> {{ apartment.numberOfRooms}} </td>
                <td> {{ apartment.numberOfGuests}} </td>
                <td> {{ apartment.timeForCheckIn}} </td>
                <td> {{ apartment.timeForCheckOut}} </td>
                <td> {{ apartment.location}} </td>

            </tr>
        </table>

        <button type="button" @click="sortAsc">SORT ASC</button>
        <button type="button" @click="sortDesc">SORT ASC</button>

    </div>
    
    `,
    methods: {
        sortAsc: function(){
        	this.multisort(this.apartments, ['pricePerNight', 'pricePerNight'], ['ASC','DESC']);
        },
        sortDesc: function(){
        	this.multisort(this.apartments, ['pricePerNight', 'pricePerNight'], ['DESC','ASC']);
        },
        multisort: function(arr, columns, order_by) {
            if(typeof columns == 'undefined') {
                columns = []
                for(x=0;x<arr[0].length;x++) {
                    columns.push(x);
                }
            }

            if(typeof order_by == 'undefined') {
                order_by = []
                for(x=0;x<arr[0].length;x++) {
                    order_by.push('ASC');
                }
            }

            function multisort_recursive(a,b,columns,order_by,index) {  
                var direction = order_by[index] == 'DESC' ? 1 : 0;

                var is_numeric = !isNaN(a[columns[index]]-b[columns[index]]);

                var x = is_numeric ? a[columns[index]] : a[columns[index]].toLowerCase();
                var y = is_numeric ? b[columns[index]] : b[columns[index]].toLowerCase();

                if(!is_numeric) {
                    x = helper.string.to_ascii(a[columns[index]].toLowerCase(),-1),
                    y = helper.string.to_ascii(b[columns[index]].toLowerCase(),-1);
                }

                if(x < y) {
                        return direction == 0 ? -1 : 1;
                }

                if(x == y)  {
                    return columns.length-1 > index ? multisort_recursive(a,b,columns,order_by,index+1) : 0;
                }

                return direction == 0 ? 1 : -1;
            }

            return arr.sort(function (a,b) {
                return multisort_recursive(a,b,columns,order_by,0);
            });
        },
      searchParam: function(event){
        event.preventDefault();
        
        axios
        .post('rest/search/apartments',{
            "location":''+ this.searchData.location,
            "checkIn" :''+ this.searchData.checkIn,
            "checkOut": this.searchData.checkOut,
            "price" :   this.searchData.price,
            "rooms" :   this.searchData.rooms,
            "maxGuests":this.searchData.maxGuests 
        })
        .then(response =>{
        	this.apartments = [];
        	response.data.forEach(el => {
        		if(el.status == "ACTIVE")
        			this.apartments.push(el);
        		});
        	return this.apartments;
        })
      },
      cancelSearch: function(){
        this.searchData.location = "";
        this.searchData.checkIn = "";
        this.searchData.checkOut = "";
        this.searchData.price = 0.0;
        this.searchData.rooms = 0;
        this.searchData.maxGuests = 0;

        axios
        .get('rest/apartments/getApartments')
        .then( response => {
        	this.apartments = [];
        	response.data.forEach(el => {
        		if(el.status == "ACTIVE")
        			this.apartments.push(el);
        		});
        	return this.apartments;
        });

      }
    },
    mounted() {
        axios
        .get('rest/apartments/getApartments')
        .then( response => {
        	response.data.forEach(el => {
        		if(el.status == "ACTIVE")
        			this.apartments.push(el);
        		});
        	return this.apartments;
        });
    },
})