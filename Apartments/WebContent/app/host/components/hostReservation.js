Vue.component("host-reservation", {
    data() {
        return {
            reservations: []
        }
    },

    template: `
    <div id = "styleForApartmentsView" >
        
    <ul>
        <li v-for="reservation in reservations">
            <h2> Guest ID: {{ reservation.guestID }} </h2>
            <h2> ID of reserved apartment: {{ reservation.idOfReservedApartment }} </h2>
            
            <h2> Start date: {{ reservation.startDateOfReservation }} </h2>
            <h2> Message for host: {{ reservation.messageForHost }} </h2>
            <h2> Status : {{ reservation.statusOfReservation }} </h2>

            <button v-if="reservation.statusOfReservation == 'KREIRANA' " type="button" @click="acceptReservation(reservation)"> Accept </button>
            <button v-if="reservation.statusOfReservation == 'KREIRANA' || reservation.statusOfReservation == 'PRIHVACENA'" type="button" @click="declineReservation(reservation)"> Decline </button>


        </li>
    </ul>

    <br>
    <table border="1">
        <tr bgcolor="lightgrey">
            <th> ID apartmana</th>
            <th> Status rezervacije </th>
            <th> startDateOfReservation </th>
            <th> Guest ID</th>
        </tr>
        <tr v-for="reservation in reservations">
            <td> {{ reservation.idOfReservedApartment }} </td>
            <td> {{ reservation.statusOfReservation }} </td>
            <td>  {{ reservation.startDateOfReservation }} </td>
            <td> {{ reservation.guestID}}  </td>
        </tr>
    </table>

    </div>
    `,
    methods: {
        acceptReservation: function (reservationParam){
            axios
                .post('rest/reservation/acceptReservation', {
                    reservation: reservationParam
                })
                .then(response => {
                    this.reservations = [];
                    response.data.forEach(el => {
                        this.reservations.push(el);
                    });
                    toastr["success"]("You make success accept for reservation !!", "Success accept!");

                    return this.reservations;
                });
        },
        declineReservation: function (reservationParam){
            axios
                .post('rest/reservation/declineReservation', {
                    reservation: reservationParam
                })
                .then(response => {
                    this.reservations = [];
                    response.data.forEach(el => {
                        this.reservations.push(el);
                    });
                    toastr["success"]("You make success decline for reservation !!", "Success decline!");

                    return this.reservations;
                });
        }
    },
    mounted() {
        axios
            .get('rest/users/getReservationsOfHost')
            .then(response => {
                this.reservations = [];
                response.data.forEach(el => {
                    this.reservations.push(el);
                });
                return this.reservations;
            });
    },
});