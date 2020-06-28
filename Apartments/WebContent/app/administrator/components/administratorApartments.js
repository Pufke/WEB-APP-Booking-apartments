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
            apartmentForChange: {},
            hideDialog: true,
            filterDataForApartment: {
                typeOfApartment: "",
                status: ""
            },
            searchField: {
                populatedPlace: '',
                maxGuests: '',
                minPrice: '',
                maxPrice: '',
                minNumberOfRooms: '',
                maxNumberOfRooms: '',
                minNumberOfGuests: '',
                maxNumberOfGuests: '',
            }
        }
    },

    template: `
    <div id = "styleForApartmentsView">


        <form method='post'>

            <input type="text" v-model="searchField.populatedPlace" placeholder="City..." >
            <input type="text" v-model="searchField.maxGuests" placeholder="Max guests in room..." >
            <br><br>

            <input type="text" v-model="searchField.minPrice" placeholder="Min price..." >
            <input type="text" v-model="searchField.maxPrice" placeholder="Max price..." >
            <br><br>

            <input type="text" v-model="searchField.minNumberOfRooms" placeholder="Min rooms..." >
            <input type="text" v-model="searchField.maxNumberOfRooms" placeholder="Max rooms..." >
            <br><br>

            <input type="text" v-model="searchField.minNumberOfGuests" placeholder="Min guests..." >
            <input type="text" v-model="searchField.maxNumberOfGuests" placeholder="Max guests..." >
            <br><br>

            <!--
            <input type="date" v-model="searchData.checkIn" placeholder="Check in...">
            <input type="date" v-model="searchData.checkOut" placeholder="Check out...">
            <input type="number" v-model="searchData.price" placeholder="Price per night..." >
            <input type="number" v-model="searchData.rooms" placeholder="Number of rooms ..." >
            <input type="number" v-model="searchData.maxGuests" placeholder="Max guests in room..." >
            -->
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
            <li v-for="apartment in filteredApartments">
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
            <tr v-for="apartment in filteredApartments">
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
            if (!this.apartmentForChange.identificator || !this.apartmentForChange.timeForCheckIn || !this.apartmentForChange.timeForCheckOut || !this.apartmentForChange.pricePerNight || !this.apartmentForChange.numberOfRooms || !this.apartmentForChange.numberOfGuests) {
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
        isMatchSearch: function (apartment) {
            // Check for location
            if (!apartment.location.address.populatedPlace.match(this.searchField.populatedPlace))
                return false;

            // Check for max guests
            if (!(apartment.numberOfGuests).toString().match(this.searchField.maxGuests))
                return false;

            // Check for price
            if (apartment.pricePerNight < parseInt(this.searchField.minPrice, 10))
                return false;
            if (apartment.pricePerNight > parseInt(this.searchField.maxPrice, 10))
                return false;

            // Check for number of rooms
            if (apartment.numberOfRooms < parseInt(this.searchField.minNumberOfRooms, 10))
                return false;
            if (apartment.numberOfRooms > parseInt(this.searchField.maxNumberOfRooms, 10))
                return false;

            // Check for number of guests in room
            if (apartment.numberOfGuests < parseInt(this.searchField.minNumberOfGuests, 10))
                return false;
            if (apartment.numberOfGuests > parseInt(this.searchField.maxNumberOfGuests, 10))
                return false;


            // If i survive all if's now i am matched search
            return true;
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
    computed: {
        filteredApartments: function () {
            return this.apartments.filter((apartment) => {
                return this.isMatchSearch(apartment);
            });
        }
    },
})
