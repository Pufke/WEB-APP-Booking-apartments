Vue.component("administrator-reservations",{
    data() {
        return {
            reservations: [],
            user: {},
            searchData: {
                usernameOfGuest : ""
            }
        }
    },
    
    template:`
    <div id = "styleForApartmentsView">
        <h1> Guten tag {{ user.userName }}, wie geht's </h1>
        
        <form method='post'>

            <input type="text" v-model="searchData.usernameOfGuest" placeholder="Username of guest..." >
            

            <button type="button" @click="searchParam" >Search</button>
            <button type="button" @click="cancelSearch">Cancel search</button>
            <button type="button" @click="sortAsc">SORT ASC</button>
            <button type="button" @click="sortDesc">SORT DESC</button>

        </form>
        <br>


        <ul>
            <li v-for="reservation in reservations">
                <h2> Cena apartmana: {{ reservation.reservedApartment.pricePerNight }} </h2>
                <h2> ID apartmana: {{ reservation.reservedApartment.identificator }} </h2>
                <h2> Tip apartmana: {{ reservation.reservedApartment.typeOfApartment }} </h2>
                <h2> Status rezervacije: {{ reservation.statusOfReservation }} </h2>
                <h2> Datum rezervacije: {{ reservation.dateOfReservation }} </h2>
                <h2> Guest username: {{ reservation.guest.userName }} </h2>
                <h2> Poruka za Host-a: {{ reservation.messageForHost }} </h2>
               
            </li>
        </ul>
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
        <th> ID apartmana</th> <th> Status rezervacije </th><th> Tip apartmana </th><th> dateOfReservation </th><th> Guests</th> </tr>
            <tr v-for="reservation in reservations">
                <td> {{ reservation.reservedApartment.identificator }} </td>
                <td> {{ reservation.statusOfReservation }} </td>
                <td> {{ reservation.reservedApartment.typeOfApartment }} </td>
                <td>  {{ reservation.dateOfReservation }} </td>
                <td> {{ reservation.guest.userName }}  </td>

            </tr>
        </table>
    </div>
    `,
    methods: {
        searchParam: function(event){
            event.preventDefault();
            alert("pozvano")
            axios
            .post('rest/search/reservations',{
                "usernameOfGuest": this.searchData.usernameOfGuest
            })
            .then(response =>{
                this.reservations = [];
                response.data.forEach(el => {
                    this.reservations.push(el);
                    });
                return this.reservations;
            })
          },
        cancelSearch: function(){
            this.searchData.usernameOfGuest = "";
    
            axios
            .get('rest/reservation/getReservations')
            .then( response => {
                this.reservations = [];
                response.data.forEach(el => {
                    this.reservations.push(el);
                    });
                return this.reservations;
            });
    
          },
        sortAsc: function(){
            let reservedApartments = [];
            let tempReservations = [];
            (this.reservations).forEach(element => reservedApartments.push(element.reservedApartment));

            reservedApartments = this.multisort(reservedApartments, ['pricePerNight', 'pricePerNight'], ['ASC','DESC']);
            
            reservedApartments.forEach(reservedApartment =>{
                this.reservations.forEach(reservation =>{
                    if(reservedApartment.identificator === reservation.reservedApartment.identificator){
                        tempReservations.push(reservation);
                    }

                });
            });

            this.reservations = tempReservations;

        },
        sortDesc: function(){
            let reservedApartments = [];
            let tempReservations = [];
            (this.reservations).forEach(element => reservedApartments.push(element.reservedApartment));

            reservedApartments = this.multisort(reservedApartments, ['pricePerNight', 'pricePerNight'], ['DESC','ASC']);
            
            reservedApartments.forEach(reservedApartment =>{
                this.reservations.forEach(reservation =>{
                    if(reservedApartment.identificator === reservation.reservedApartment.identificator){
                        tempReservations.push(reservation);
                    }

                });
            });

            this.reservations = tempReservations;
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
                    /*
                    *   If we have string, then convert it to
                    *   array of charachter with .split("")
                    *   then go through every ellement and 
                    *   get ascii value from it and add that to sum
                    *   of that word, with that, we have uniq value for every
                    *   word.
                    *    author: vaxi
                    */
                    let sum_x=0;
                    let sum_y=0;
                    
                    x.split("").forEach(element => sum_x += element.charCodeAt())
                    y.split("").forEach(element => sum_y += element.charCodeAt())

                    x= sum_x;
                    y=sum_y;
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
    },
    mounted() {
        let one = 'rest/reservation/getReservations';
        let two = 'rest/edit/profileUser';

        let requestOne = axios.get(one);
        let requestTwo = axios.get(two);

        axios.all([requestOne, requestTwo]).then(axios.spread((...responses) => {
        	
        	responses[0].data.forEach(el => {
        		this.reservations.push(el);
                });
             //this.reservations = responses[0].data;
             this.user = responses[1].data;
        })).catch(errors => {
            console.log("Greska brt");
        })
    }
});