Vue.component("view-apartments",{
    data() {
        return {
            apartments: {},
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

    </div>
    
    `,
    methods: {
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
            this.apartments = response.data;
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
            this.apartments = response.data;
        });

      }
    },
    mounted() {
        axios
        .get('rest/apartments/getApartments')
        .then( response => {
            this.apartments = response.data;
        });
    },
})