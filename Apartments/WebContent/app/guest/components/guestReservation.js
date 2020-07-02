Vue.component("guest-reservation", {

    data() {
        return {
            reservations: [],
            user: {},
            ocena : "",
            komentar : "",
            searchData: {
                location: "",
                checkIn: "",
                checkOut: "",
                price: 0.0,
                rooms: 0,
                maxGuests: 0
            }
        }
    },

    template: `
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
        
    	<h1> Trenutno ulogovani korisnik je {{ user.userName }} i ovo su rezervacije samo za tog korisnika!! :) </h1>
        <ul>
            <li v-for="reservation in reservations">
            	<h2> Reservation ID: {{ reservation.id }} </h2>
                <h2> Apartment ID: {{ reservation.idOfReservedApartment }} </h2>
                <h2> Total price: {{ reservation.totalPrice }} </h2>
                <h2> Start date: {{ reservation.startDateOfReservation }} </h2>
                <h2> Guest ID: {{ reservation.guestID }} </h2>
                <h2> Message for Host: {{ reservation.messageForHost }} </h2>
                <h2> Status of reservation: {{ reservation.statusOfReservation }} </h2>
                <button @click="deleteReservation(reservation.id, reservation.idOfReservedApartment)">DELETE RESERVATION</button>
    	       
    	        <input  v-if="reservation.statusOfReservation == 'ODBIJENA' || reservation.statusOfReservation == 'ZAVRSENA'" v-model="komentar" placeholder="Vas komentar o apartmanu">
    	        <input  v-if="reservation.statusOfReservation == 'ODBIJENA' || reservation.statusOfReservation == 'ZAVRSENA'" v-model="ocena" placeholder="Vasa ocena o apartmanu">
    	        
    	       
          
            	    <button v-if="reservation.statusOfReservation == 'ODBIJENA' || reservation.statusOfReservation == 'ZAVRSENA'" type="button" @click="submitKomentar(reservation.idOfReservedApartment,komentar,ocena)">SUBMIT</button>
            
            </li>
        </ul>
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
        <th> ID apartmana</th> <th> Status rezervacije </th><th> startDateOfReservation </th><th> Guest ID </th> </tr>
            <tr v-for="reservation in reservations">
                <td> {{ reservation.idOfReservedApartment }} </td>
                <td> {{ reservation.statusOfReservation }} </td>
                <td>  {{ reservation.startDateOfReservation }} </td>
                <td> {{ reservation.guestID }}  </td>
    			
            </tr>
        </table>
        
         <button type="button" @click="sortAsc">SORT ASC</button>
         <button type="button" @click="sortDesc">SORT ASC</button>
        
    </div>
    
    `,
    /*
guestID:3
id:2
idOfReservedApartment:2
logicalDeleted:0
messageForHost:"Ako moze samo da me docekaju otvoreni prozori"
numberOfNights:2
startDateOfReservation:1593043200000
statusOfReservation:"Kreirana"
totalPrice:99
    */
    methods: {
        sortAsc: function () {
            this.multisort(this.reservations, ['totalPrice', 'totalPrice'], ['ASC', 'DESC']);
        },
        sortDesc: function () {
            this.multisort(this.reservations, ['totalPrice', 'totalPrice'], ['DESC', 'ASC']);
        },
        multisort: function (arr, columns, order_by) {
            if (typeof columns == 'undefined') {
                columns = []
                for (x = 0; x < arr[0].length; x++) {
                    columns.push(x);
                }
            }

            if (typeof order_by == 'undefined') {
                order_by = []
                for (x = 0; x < arr[0].length; x++) {
                    order_by.push('ASC');
                }
            }

            function multisort_recursive(a, b, columns, order_by, index) {
                var direction = order_by[index] == 'DESC' ? 1 : 0;

                var is_numeric = !isNaN(a[columns[index]] - b[columns[index]]);

                var x = is_numeric ? a[columns[index]] : a[columns[index]].toLowerCase();
                var y = is_numeric ? b[columns[index]] : b[columns[index]].toLowerCase();

                if (!is_numeric) {
                    /*
                        If we have string, then convert it to
                        array of charachter with .split("")
                        then go through every ellement and 
                        get ascii value from it and add that to sum
                        of that word, with that, we have uniq value for every
                        word.

                        author: vaxi
                    */
                    let sum_x = 0;
                    let sum_y = 0;

                    x.split("").forEach(element => sum_x += element.charCodeAt())
                    y.split("").forEach(element => sum_y += element.charCodeAt())

                    x = sum_x;
                    y = sum_y;
                }

                if (x < y) {
                    return direction == 0 ? -1 : 1;
                }

                if (x == y) {
                    return columns.length - 1 > index ? multisort_recursive(a, b, columns, order_by, index + 1) : 0;
                }

                return direction == 0 ? 1 : -1;
            }

            return arr.sort(function (a, b) {
                return multisort_recursive(a, b, columns, order_by, 0);
            });
        },
        submitKomentar: function (apartmentID, komentar, ocena) {
            axios
                .post('rest/reservation/makeComment', {
                    "guestUserName": this.user.userName,
                    "apartmentID": apartmentID,
                    "txtOfComment": komentar,
                    "ratingForApartment": ocena,
                })
                .then(response => {

                    toastr["success"]("Success commented!!", "Success!");
                })
                .catch(err => {
                    toastr["error"]("Failed during commenting :(", "Fail");
                })
        },
        deleteReservation: function (identificator, apartmentID) {
            axios
                .post('rest/reservation/deleteReservations', {
                    "reservationID": identificator,
                    "apartmentIdentificator": apartmentID,
                })
                .then(response => {
                    filteredReservations = [];
                    this.reservations.forEach(el => {

                        if (el.id != identificator) {
                            filteredReservations.push(el);
                        }
                    });
                    this.reservations = filteredReservations;
                    toastr["success"]("Success deleted!!", "Success!");
                })
                .catch(err => {
                    toastr["error"]("Failed during changes :(", "Fail");
                })
        },
        searchParam: function (event) {
            event.preventDefault();

            axios
                .post('rest/search/SearchReservations', {
                    "location": '' + this.searchData.location,
                    "checkIn": '' + this.searchData.checkIn,
                    "checkOut": this.searchData.checkOut,
                    "price": this.searchData.price,
                    "rooms": this.searchData.rooms,
                    "maxGuests": this.searchData.maxGuests
                })
                .then(response => {
                    this.reservations = [];
                    response.data.forEach(el => {
                        if (el.guest.userName == this.user.userName) {
                            this.reservations.push(el);
                        }
                    });
                    return this.reservations;
                })
        },
        cancelSearch: function () {
            this.searchData.location = "";
            this.searchData.checkIn = "";
            this.searchData.checkOut = "";
            this.searchData.price = 0.0;
            this.searchData.rooms = 0;
            this.searchData.maxGuests = 0;

            axios
                .get('rest/reservation/getReservations')
                .then(response => {
                    this.reservations = [];
                    response.data.forEach(el => {
                        console.log(this.user.userName);
                        if (el.guest.userName == this.user.userName) {
                            this.reservations.push(el);
                        }
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
                if (el.guestID == responses[1].data.id) {
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