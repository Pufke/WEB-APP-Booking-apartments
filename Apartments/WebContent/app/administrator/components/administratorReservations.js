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
            

        </form>
        <br>


        <ul>
            <li v-for="reservation in reservations">
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
    
          }
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