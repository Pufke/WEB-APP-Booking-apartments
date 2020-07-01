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
// pricePerNight
Vue.component("host-ActiveApartments", {
    data() {
        return {
        	startDateForHost: null,
        	endDateForHost: null,
            apartments: [],
            amenities: [],          // need it for adding form of new apartment ( we need available amenities )
            apartmentForChange: {},
            hideDialog: true,
            hideAddDialog: true,
            newItemName: "",
            newApartment: {
                apartmentAmentitiesIDs: [],
                apartmentCommentsIDs: [],
                availableDates: [],
                datesForHosting: [],
                hostID: 1,
                id: 98989,
                imagesPath: "empty",
                listOfReservationsIDs: [],
                location: {
                    address: {
                        number: null,
                        populatedPlace: null,
                        street: null,
                        zipCode: null
                    },
                    latitude: null,
                    longitude: null
                },
                logicalDeleted: 0,
                numberOfGuests: null,
                numberOfRooms: null,
                pricePerNight: null,
                status: "INACTIVE",
                timeForCheckIn: "14:00",
                timeForCheckOut:  "10:00",
                typeOfApartment: null,
            },
            filterDataForApartment: {
                typeOfApartment: "",
                selectedAmenities: [],
                status: ""
            },
            searchField: '',
            selected: [] // ovo sam uzeo samo privremeno dok ne sredimo u apartmanu listu sadrzaja koju on ima

        }
    },
    template: `
    <div id = "styleForApartmentsView">

        <!-- Search & filter & sort & adding new apartment-->
        <form method='post'>

            <input type="text"  placeholder="Username of guest..." >
            <button type="button" @click="sortAsc">SORT ASC</button>
            <button type="button" @click="sortDesc">SORT DESC</button>

            <br>
            <button type="button" @click="addItem()"> Add new item </button>
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

            <!-- List of all amenities in apartments -->
            <select v-model="filterDataForApartment.selectedAmenities" multiple @change="onchangeAmenities()">

                <option value=""> Without filter for amenities </option>
                <option v-for="option in amenities" v-bind:value="option.id">
                    {{ option.itemName }}
                </option>

            </select>
            <!-- End list of all amenities in apartments -->

        </form>

        <!-- End of search & filter & sort & adding new apartment -->
        <br>


        

        <!-- Cards for apartments -->
        <ul>
            <li v-for="apartment in apartments">

                <h2> Type of apartment: {{ apartment.typeOfApartment }} </h2>
                <h2> Price per night: {{ apartment.pricePerNight}} </h2>
                <h2> ID of apartment: {{apartment.id }}  </h2>

                <h2 v-for="amenitiesID in apartment.apartmentAmentitiesIDs">
                    Amenities ID: {{ amenitiesID }}
                </h2>

                <button type="button" @click="changeApartment(apartment)"> Change </button>
                <button type="button" @click="deleteApartment(apartment)"> Delete </button>
            </li>
        </ul> 
        <!-- End of cards for apartments -->
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
            <th> Status </th><th> Type </th><th> Price </th><th> Rooms </th></tr>
            <tr v-for="apartment in apartments">
                <td> {{ apartment.status }} </td>
                <td> {{ apartment.typeOfApartment }} </td>
                <td> {{ apartment.pricePerNight}} </td>
                <td> {{ apartment.numberOfRooms}} </td>
            </tr>
        </table>



        <!-- Modal dialog section for changing -->
        <div id = "dijalogDeo" v-bind:class="{bgModal: hideDialog, bgModalShow: !hideDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideDialog = !hideDialog">+</div>

                <form method='post'>     
                    <input type="text" v-model="newApartment.typeOfApartment" placeholder="Type of apartment...">
                    
                    <label for="checkIn">Check in time:</label>
                    <input  name="checkIn" type="time" v-model="newApartment.timeForCheckIn" placeholder="Check in...">
                   
                    <label for="checkOut">Check out time:</label>
                    <input name="checkOut"  type="time" v-model="newApartment.timeForCheckOut" placeholder="Check out...">
                    
                    
                    <label for="startDate">Start date for host:</label>
                    <input name="startDate" type="date" v-model="startDateForHost" >
                
                    <label for="endDate">End date for host:</label>
    	            <input name="endDate" type="date" v-model="endDateForHost">
    	            
                    <input  type="number" v-model="newApartment.pricePerNight" placeholder="Price per night..." >
                    <input  type="number" v-model="newApartment.numberOfRooms" placeholder="Number of rooms ..." >
                    <input  type="number" v-model="newApartment.numberOfGuests" placeholder="Max guests in room..." >    

                    <!-- Address -->
                    <input type="text" v-model="newApartment.location.address.populatedPlace" placeholder="Town name ...">
                    <input type="text" v-model="newApartment.location.address.street" placeholder="Street ...">
                    <input type="text" v-model="newApartment.location.address.number" placeholder="Number ...">
                    <!-- End of address -->


                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal dialog section -->

        <!-- Modal DIALOG section for ADDING -->
        <div id = "addDialogForApartments" v-bind:class="{bgModal: hideAddDialog, bgModalShow: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>

                <form method='post'>
                    <input type="text" v-model="newApartment.typeOfApartment" placeholder="Type of apartment...">
                   
                    <label for="checkIn">Check in time:</label>
                    <input  name="checkIn" type="time" v-model="newApartment.timeForCheckIn" placeholder="Check in...">
                   
                    <label for="checkOut">Check out time:</label>
                    <input name="checkOut"  type="time" v-model="newApartment.timeForCheckOut" placeholder="Check out...">
                   
                    <label for="startDate">Start date for host:</label>
                    <input name="startDate" type="date" v-model="startDateForHost" >
                
                    <label for="endDate">End date for host:</label>
    	            <input name="endDate" type="date" v-model="endDateForHost">
                
                
                    <input  type="number" v-model="newApartment.pricePerNight" placeholder="Price per night..." >
                    <input  type="number" v-model="newApartment.numberOfRooms" placeholder="Number of rooms ..." >
                    <input  type="number" v-model="newApartment.numberOfGuests" placeholder="Max guests in room..." >

                    <!-- Address -->
                    <input type="text" v-model="newApartment.location.address.populatedPlace" placeholder="Town name ...">
                    <input type="text" v-model="newApartment.location.address.street" placeholder="Street ...">
                    <input type="text" v-model="newApartment.location.address.number" placeholder="Number ...">
                    <!-- End of address -->

                    <!-- List of amenities in apartments -->
                    <select v-model="newApartment.apartmentAmentitiesIDs" multiple>
                        <option v-for="option in amenities" v-bind:value="option.id">
                            {{ option.itemName }}
                        </option>
                    </select>
                    <!-- End list of amenities in apartments -->

                    <button type="button" @click="confirmAdding">Confirm</button>
                    <button type="button" @click="hideAddDialog = !hideAddDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal adding dialog section -->

    </div>
    
    `,
    methods: {
        onchangeTypeOfApartment: function () {
            if (this.filterDataForApartment.typeOfApartment == "") {
                // Reset to all apartments
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/apartments/getMyApartments')
                    .then(response => {
                        this.apartments=[];
                        response.data.forEach(el => {
                            if (el.status == "ACTIVE")
                                this.apartments.push(el);
                        });
                        return this.apartments;
                    });

            } else {
                let tempApartments = (this.apartments).filter(apartment => apartment.typeOfApartment == this.filterDataForApartment.typeOfApartment);
                this.apartments = tempApartments;
            }
        },
        onchangeAmenities: function () {
            if (this.filterDataForApartment.selectedAmenities == "") {
                // Reset to all apartments
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/apartments/getMyApartments')
                    .then(response => {
                        this.apartments=[];
                        response.data.forEach(el => {
                            if (el.status == "ACTIVE")
                                this.apartments.push(el);
                        });
                        return this.apartments;
                    });

            } else {
                /*
                    Put apartment in list of apartments.
                    If amenities of selected amenities in filter is subset of amenities of this apartment(which i am adding).

                    ref: https://stackoverflow.com/questions/38811421/how-to-check-if-an-array-is-a-subset-of-another-array-in-javascript/48211214#48211214
                    author: vaxi
                */
                let tempApartments = (this.apartments).filter(apartment => this.filterDataForApartment.selectedAmenities.every(val => apartment.apartmentAmentitiesIDs.includes(val)));
                this.apartments = tempApartments;

            }
        },
        onchangeStatus: function () {
            if (this.filterDataForApartment.status == "") {
                // Reset to all apartments
                //TODO: Staviti ovde logiku da pokaze one koji su prethodno bili
                // ne ovako da uzme sve kada se iskljuci filter
                axios
                    .get('rest/apartments/getMyApartments')
                    .then(response => {
                        this.apartments=[];
                        response.data.forEach(el => {
                            if (el.status == "ACTIVE")
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

            this.newApartment = apartment;

        },
        confirmChanging: function () {
        
            // Check is empty field input
            // ref: https://stackoverflow.com/questions/5515310/is-there-a-standard-function-to-check-for-null-undefined-or-blank-variables-in
            if (!this.newApartment.id || !this.newApartment.timeForCheckIn || !this.newApartment.timeForCheckOut
                || !this.newApartment.pricePerNight || !this.newApartment.numberOfRooms || !this.newApartment.numberOfGuests) {
                toastr["warning"]("All field is required", "Watch out !");
                return;

            }

            axios
                .post('rest/apartments/changeMyApartment', {
                    addedApartment: this.newApartment,
                    "startDateForReservation": this.startDateForHost,
                    "endDateForReservation" : this.endDateForHost
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success change !!", "Success changes!");

                    return this.apartments;
                });
        },
        addItem: function () {
            this.hideAddDialog = !this.hideAddDialog;
        },
        confirmAdding: function () {
        	console.log("<<<<" , this.startDateForReservation);
        	console.log(">>>>>", this.endDateForReservation);
            // warning/error if some fields are null or empty
            // ref: https://stackoverflow.com/questions/5515310/is-there-a-standard-function-to-check-for-null-undefined-or-blank-variables-in
            if (!this.newApartment.timeForCheckIn || !this.newApartment.timeForCheckOut || 
                !this.newApartment.pricePerNight || !this.newApartment.numberOfRooms ||
                !this.newApartment.numberOfGuests || !this.newApartment.location.address.populatedPlace ||
                !this.newApartment.location.address.street || !this.newApartment.location.address.number
            ) {

                toastr["warning"]("All field is required", "Watch out !");
                return;
            }
            axios
                .post('rest/apartments/addNewApartments', {
                    addedApartment: this.newApartment,
                    "startDateForReservation": this.startDateForHost,
                    "endDateForReservation" : this.endDateForHost
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success adding !!", "Success adding!");
                    return this.apartments;
                });

        },
        deleteApartment: function (apartment) {
            axios
                .delete('rest/apartments/deleteHostApartment', {
                    data: {
                        "hostID": 1,
                        "identificator": apartment.id
                    }


                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success delete !!", "Success delete!");

                    return this.apartments;
                });
        },
        sortAsc: function () {
            let tempApartments = [];

            (this.apartments).forEach(element => tempApartments.push(element));
            tempApartments = this.multisort(tempApartments, ['pricePerNight', 'pricePerNight'], ['ASC', 'DESC']);

            this.apartments = tempApartments;

        },
        sortDesc: function () {
            let tempApartments = [];

            (this.apartments).forEach(element => tempApartments.push(element));
            tempApartments = this.multisort(tempApartments, ['pricePerNight', 'pricePerNight'], ['DESC', 'ASC']);

            this.apartments = tempApartments;
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
    },
    mounted() {
        axios
            .get('rest/apartments/getMyApartments')
            .then(response => {
                this.apartments=[];
                response.data.forEach(el => {
                    if (el.status == "ACTIVE")
                        this.apartments.push(el);
                });
                return this.apartments;
            });

        axios
            .get('rest/amenities/getAmenities')
            .then(response => {
                this.amenities=[];
                response.data.forEach(el => {
                    this.amenities.push(el);
                });
                return this.amenities;
            });

        axios
            .get('rest/edit/profileUser')
            .then(response => this.newApartment.hostID = response.data.id)

        // Just take model of apartment
        axios
            .get('rest/apartments/getDummyApartments')
            .then(response => {
                this.apartmentForChange = response.data
            });
    },



});