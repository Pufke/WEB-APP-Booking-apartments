Vue.component("host-reservation",{
    data() {
        return {
            reservations: []
        }
    },

    template:`
    <div id = "styleForApartmentsView" >
        
    <ul>
    <li v-for="reservation in reservations">
        <h2> Guest ID: {{ reservation.guestID }} </h2>
        <h2> ID of reserved apartment: {{ reservation.idOfReservedApartment }} </h2>
        
        <h2> Start date: {{ reservation.startDateOfReservation }} </h2>
        <h2> Message for host: {{ reservation.messageForHost }} </h2>
        <h2> Status : {{ reservation.statusOfReservation }} </h2>
       
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