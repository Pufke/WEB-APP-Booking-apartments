Vue.component("guest-reservation",{

    data() {
        return {
            reservations: [],
            user: {},
            ocena: "ocena od 1 do 5",
            komentar:"",
            searchData: {
      
            }
        }
    },

    template:`
    <div id = "styleForApartmentsView">
    	<h1> Trenutno ulogovani korisnik je {{ user.userName }} i ovo su rezervacije samo za tog korisnika!! :) </h1>
        <ul>
            <li v-for="reservation in reservations">
            	<h2> ID rezervacije: {{ reservation.reservationID }} </h2>
                <h2> ID apartmana: {{ reservation.reservedApartment.identificator }} </h2>
                <h2> Tip apartmana: {{ reservation.reservedApartment.typeOfApartment }} </h2>
                <h2> Status rezervacije: {{ reservation.statusOfReservation }} </h2>
                <h2> Datum rezervacije: {{ reservation.dateOfReservation }} </h2>
                <h2> Guest username: {{ reservation.guest.userName }} </h2>
                <h2> Poruka za Host-a: {{ reservation.messageForHost }} </h2>
                <button @click="deleteReservation(reservation.reservationID, reservation.reservedApartment.identificator)">DELETE RESERVATION</button>
    	       
    	        <input v-model="komentar" placeholder="Vas komentar o apartmanu">
    	        <input v-model="ocena" placeholder="Vasa ocena o apartmanu">
    	        
    	        <button @click="submitKomentar(reservation.reservedApartment.identificator,komentar,ocena)">SUBMIT</button>
            </li>
        </ul>
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
        <th> ID apartmana</th> <th> Status rezervacije </th><th> Tip apartmana </th><th> dateOfReservation </th><th> Guests</th> </tr>
            <tr v-for="reservation in reservations">
                <td> {{ reservation.reservedApartment.identificator }} </td>
                <td> {{ reservation.reservedApartment.statusOfReservation }} </td>
                <td> {{ reservation.reservedApartment.typeOfApartment }} </td>
                <td>  {{ reservation.dateOfReservation }} </td>
                <td> {{ reservation.guest.userName }}  </td>
    			
            </tr>
        </table>
    </div>
    
    `,
    methods: {
    	submitKomentar: function(apartmentID,komentar,ocena){
    		axios
            .post('rest/reservation/makeComment',{
                 "guestUserName": this.user.userName,
                 "apartmentID": apartmentID,
                 "txtOfComment": komentar,
                 "ratingForApartment": ocena,
           })
           .then(response =>{
          	 
              toastr["success"]("Success changes!!", "Success!");
           })
           .catch(err => {
             toastr["error"]("Failed during changes :(", "Fail");
           })     
    	},
    	deleteReservation: function(identificator, apartmentID){
      	  axios
            .post('rest/reservation/deleteReservations',{
                 "reservationID": identificator,
                 "apartmentIdentificator": apartmentID,
           })
           .then(response =>{
          	 filteredReservations = [];
          	 this.reservations.forEach(el => {     
        
       		 	if(el.reservationID != identificator){
       		 	   filteredReservations.push(el);
        		}
              });
              this.reservations = filteredReservations;
              toastr["success"]("Success changes!!", "Success!");
           })
           .catch(err => {
             toastr["error"]("Failed during changes :(", "Fail");
           })     
        }
    },
    mounted() {
        let one = 'rest/reservation/getReservations';
        let two = 'rest/edit/profileUser';

        let requestOne = axios.get(one);
        let requestTwo = axios.get(two);

        axios.all([requestOne, requestTwo]).then(axios.spread((...responses) => {    
        	responses[0].data.forEach(el => {
        		if(el.guest.userName == responses[1].data.userName){
        			this.reservations.push(el);
        		}
                });
             //this.reservations = responses[0].data;
             this.user = responses[1].data;
        })).catch(errors => {
            console.log("Greska brt");
        })
    },
    
})