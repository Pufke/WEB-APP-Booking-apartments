Vue.component("host-reservation", {
    data() {
        return {
            reservations: [],
            users: [],
            filterDataForReservation: {
                status: ""
            },
            searchField: '',
            previewSearch: false,
        }
    },

    template: `
    <div id = "styleForApartmentsView" >
        
        <button type="button" @click=" previewSearch = !previewSearch " class="btn"><i class="fa fa-search" aria-hidden="true"></i> FILTERS </button> <br><br>

        <!-- Search & filter & sort -->
        <form method='post' v-if="previewSearch" >

            <input type="text" v-model="searchField"  placeholder="Username of guest which make reservation..." >
            <button type="button" @click="sortAsc">SORT ASC</button>
            <button type="button" @click="sortDesc">SORT DESC</button>

            <!-- If user don't want use filter, check just option: Without filter for status -->
            <select v-model="filterDataForReservation.status" @change="onchangeStatus()">
                <option value="">Without filter for status </option>
                <option>KREIRANA</option>
                <option>PRIHVACENA</option>
                <option>ODBIJENA</option>
            </select>

        </form>

        <!-- End of search & filter & sort -->
        <br>



        <ul>
            <li v-for="reservation in filteredReservations">
                <h2> Guest ID: {{ reservation.guestID }} </h2>
                <h2> Guest userName: {{ getGuestUserNameById(reservation.guestID) }} </h2>
                <h2> ID of reserved apartment: {{ reservation.idOfReservedApartment }} </h2>
                
                <h2> Start date: {{ reservation.startDateOfReservation }} </h2>
                <h2> Message for host: {{ reservation.messageForHost }} </h2>
                <h2> Total price: {{ reservation.totalPrice }} </h2>
                <h2> Status : {{ reservation.statusOfReservation }} </h2>

                <button v-if="reservation.statusOfReservation == 'KREIRANA' " type="button" @click="acceptReservation(reservation)"> Accept </button>
                <button v-if="reservation.statusOfReservation == 'KREIRANA' || reservation.statusOfReservation == 'PRIHVACENA'" type="button" @click="declineReservation(reservation)"> Decline </button>
                <button v-if="reservation.statusOfReservation == 'PRIHVACENA'" type="button" @click="endReservation(reservation)"> End </button>


            </li>
        </ul>

        <br>

        <!-- Table of host reservations -->
        <div class="styleForTable" >
            <table style="width:100%">

            <thead>
                <tr>
                    <th> ID apartmana</th>
                    <th> Status rezervacije </th>
                    <th> startDateOfReservation </th>
                    <th> Guest ID</th>
                    <th> Total price </th>
                </tr>
            </thead>

            <tbody>
                <tr v-for="reservation in filteredReservations">
                    <td> {{ reservation.idOfReservedApartment }} </td>
                    <td> {{ reservation.statusOfReservation }} </td>
                    <td>  {{ reservation.startDateOfReservation }} </td>
                    <td> {{ reservation.guestID }}  </td>
                    <td> {{ reservation.totalPrice }} </td>
                </tr>
            </tbody> 

            </table>
        </div>
        <!-- End of table for host reservations -->

    </div>
    `,
    methods: {
        acceptReservation: function (reservationParam) {
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
        declineReservation: function (reservationParam) {
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
        },
        endReservation: function (reservationParam) {
            axios
                .post('rest/reservation/endReservation', {
                    reservation: reservationParam
                })
                .then(response => {
                    this.reservations = [];
                    response.data.forEach(el => {
                        this.reservations.push(el);
                    });
                    toastr["success"]("You make success end for reservation !!", "Success end of Reservation!");

                    return this.reservations;
                });
        },
        onchangeStatus: function () {
            if (this.filterDataForReservation.status == "") {
                // Reset to all reservations
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/users/getReservationsOfHost')
                    .then(response => {
                        this.reservations = [];
                        response.data.forEach(el => {
                            this.reservations.push(el);
                        });
                        return this.reservations;
                    });

            } else { // show reservation with only this status of reservation
                let tempReservations = (this.reservations).filter(reservation => reservation.statusOfReservation == this.filterDataForReservation.status);
                this.reservations = tempReservations;
            }
        },
        sortAsc: function () {
            let hostReservations = [];

            (this.reservations).forEach(element => hostReservations.push(element));
            hostReservations = this.multisort(hostReservations, ['totalPrice', 'totalPrice'], ['ASC', 'DESC']);

            this.reservations = hostReservations;

        },
        sortDesc: function () {
            let hostReservations = [];

            (this.reservations).forEach(element => hostReservations.push(element));
            hostReservations = this.multisort(hostReservations, ['totalPrice', 'totalPrice'], ['DESC', 'ASC']);

            this.reservations = hostReservations;
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
                    *   If we have string, then convert it to
                    *   array of charachter with .split("")
                    *   then go through every ellement and 
                    *   get ascii value from it and add that to sum
                    *   of that word, with that, we have uniq value for every
                    *   word.
                    *    author: vaxi
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
        getGuestUserNameById : function(idOfGuest){
            let UserObj = this.users.find( user => user.id == idOfGuest);
            if(UserObj)
                return UserObj.userName;
            return '';
        },
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

        axios
            .get('rest/users/getGuestsOfHost')
            .then(response => {
                this.users = [];
                response.data.forEach(el => {
                    this.users.push(el);
                });
                return this.users;
            });
    },
    computed: {
        filteredReservations: function () {
            return this.reservations.filter((reservation) => {
                return this.getGuestUserNameById(reservation.guestID).match(this.searchField);
            });
        }
    },
    
});