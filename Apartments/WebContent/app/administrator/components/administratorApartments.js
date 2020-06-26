/**
 * Settings for toastr.
 */
toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": true,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "preventDuplicates": false,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

Vue.component("administrator-apartments", {
    data() {
        return {
            apartments: [],
            searchData: {
                location: "",
                checkIn: "",
                checkOut: "",
                price: 0.0,
                rooms: 0,
                maxGuests: 0
            },
            apartmentForChange: {},
            hideDialog: true,
            filterDataForApartment: {
                typeOfApartment: "",
                status: ""
            }
        }
    },

    template: `
    <div id = "styleForApartmentsView">


        <form method='post'>

            <input type="text" v-model="searchData.location" placeholder="Location..." >
            <input type="date" v-model="searchData.checkIn" placeholder="Check in...">
            <input type="date" v-model="searchData.checkOut" placeholder="Check out...">
            <input type="number" v-model="searchData.price" placeholder="Price per night..." >
            <input type="number" v-model="searchData.rooms" placeholder="Number of rooms ..." >
            <input type="number" v-model="searchData.maxGuests" placeholder="Max guests in room..." >

            <button type="button" @click="searchParam" >Search</button>
            <button type="button" @click="cancelSearch">Cancel search</button>
            <br><br>

            <!-- If user don't want use filter, check just option: Without filter for type -->
            <select v-model="filterDataForApartment.typeOfApartment" @change="onchangeTypeOfApartment()">
                <option value="">Without filter for type </option>
                <option>ROOM</option>
                <option>STANDARD</option>
            </select>

            <!-- If user don't want use filter, check just option: Without filter for status -->
            <select v-model="filterDataForApartment.status" @change="onchangeStatus()">
                <option value="">Without filter for status </option>
                <option>ACTIVE</option>
                <option>INACTIVE</option>
            </select>

            <br><br>
            <button type="button" @click="sortAsc">SORT ASC</button>
            <button type="button" @click="sortDesc">SORT DESC</button>

        </form>
        <br>


        <ul>
            <li v-for="apartment in apartments">
                <h2> {{ apartment.typeOfApartment }} </h2>
                <h2> {{ apartment.pricePerNight}} </h2>

                <button type="button" @click="changeApartment(apartment)"> Change </button>
                <button type="button" v-if="apartment.status == 'INACTIVE' "> Activate </button>
                <button type="button" @click="deleteApartment(apartment)"> Delete </button>
            
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

        <!-- Modal dialog section for changing -->
        <div id = "dijalogDeo" v-bind:class="{bgModal: hideDialog, bgModalShow: !hideDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideDialog = !hideDialog">+</div>
                <img src="http://icons.iconarchive.com/icons/cjdowner/cryptocurrency-flat/128/ICON-ICX-icon.png" alt="">


                <form method='post'>

                    
                    <input  type="date" v-model="apartmentForChange.timeForCheckIn" placeholder="Check in...">
                    <input  type="date" v-model="apartmentForChange.timeForCheckOut" placeholder="Check out...">
                    <input  type="number" v-model="apartmentForChange.pricePerNight" placeholder="Price per night..." >
                    <input  type="number" v-model="apartmentForChange.numberOfRooms" placeholder="Number of rooms ..." >
                    <input  type="number" v-model="apartmentForChange.numberOfGuests" placeholder="Max guests in room..." >

                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal dialog section -->

    </div>

    `,
    methods: {
        onchangeTypeOfApartment: function () {
            if (this.filterDataForApartment.typeOfApartment == "") {
                // Reset to all apartments
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/apartments/getApartments')
                    .then(response => {
                        this.apartments = [];
                        response.data.forEach(el => {
                            if (el.status == "ACTIVE" || el.status == "INACTIVE")
                                this.apartments.push(el);
                        });
                        return this.apartments;
                    });

            } else {
                let tempApartments = (this.apartments).filter(apartment => apartment.typeOfApartment == this.filterDataForApartment.typeOfApartment);
                this.apartments = tempApartments;
            }
        },
        onchangeStatus: function () {
            if (this.filterDataForApartment.status == "") {
                // Reset to all apartments
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/apartments/getApartments')
                    .then(response => {
                        this.apartments = [];
                        response.data.forEach(el => {
                            if (el.status == "ACTIVE" || el.status == "INACTIVE")
                                this.apartments.push(el);
                        });
                        return this.apartments;
                    });

            } else {
                let tempApartments = (this.apartments).filter(apartment => apartment.status == this.filterDataForApartment.status);
                this.apartments = tempApartments;
            }
        },
        changeApartment: function (apartment) {
            this.hideDialog = !this.hideDialog;

            this.apartmentForChange = apartment;

        },
        confirmChanging: function () {

            // Check is empty field input
            // ref: https://stackoverflow.com/questions/5515310/is-there-a-standard-function-to-check-for-null-undefined-or-blank-variables-in
            if( !this.apartmentForChange.identificator || !this.apartmentForChange.timeForCheckIn || !this.apartmentForChange.timeForCheckOut || !this.apartmentForChange.pricePerNight || !this.apartmentForChange.numberOfRooms || !this.apartmentForChange.numberOfGuests){
                toastr["warning"]("All field is required", "Watch out !");
                return;
            }

            axios
                .post('rest/apartments/changeApartment', {
                    "identificator": this.apartmentForChange.identificator,
                    "timeForCheckIn": this.apartmentForChange.timeForCheckIn,
                    "timeForCheckOut": this.apartmentForChange.timeForCheckOut,
                    "pricePerNight": this.apartmentForChange.pricePerNight,
                    "numberOfRooms": this.apartmentForChange.numberOfRooms,
                    "numberOfGuests": this.apartmentForChange.numberOfGuests
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE" || el.status == "INACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success change !!", "Success changes!");

                    return this.apartments;
                });
        },
        deleteApartment: function (apartment) {
            this.apartmentForChange = apartment;
            this.requestForDeleteApartment();
        },
        requestForDeleteApartment: function () {
            axios
                .delete('rest/apartments/deleteApartment', {
                    data: {
                        "hostID": 1,
                        "identificator": this.apartmentForChange.id
                    }


                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE" || el.status == "INACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success delete !!", "Success delete!");

                    return this.apartments;
                });
        },
        sortAsc: function () {
            this.multisort(this.apartments, ['pricePerNight', 'pricePerNight'], ['ASC', 'DESC']);
        },
        sortDesc: function () {
            this.multisort(this.apartments, ['pricePerNight', 'pricePerNight'], ['DESC', 'ASC']);
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
        searchParam: function (event) {
            event.preventDefault();

            axios
                .post('rest/search/apartments', {
                    "location": '' + this.searchData.location,
                    "checkIn": '' + this.searchData.checkIn,
                    "checkOut": this.searchData.checkOut,
                    "price": this.searchData.price,
                    "rooms": this.searchData.rooms,
                    "maxGuests": this.searchData.maxGuests
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE")
                            this.apartments.push(el);
                    });
                    return this.apartments;
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
                .get('rest/apartments/getApartments')
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE")
                            this.apartments.push(el);
                    });
                    return this.apartments;
                });

        }
    },
    mounted() {
        axios
            .get('rest/apartments/getApartments')
            .then(response => {
                response.data.forEach(el => {
                    if (el.status == "ACTIVE" || el.status == "INACTIVE")
                        this.apartments.push(el);
                });
                return this.apartments;
            });
    },
})
