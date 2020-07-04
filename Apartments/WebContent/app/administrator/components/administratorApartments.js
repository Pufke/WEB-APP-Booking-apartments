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
            startDateForHost: null,
            endDateForHost: null,
            apartments: [],
            amenities: [], 
            apartmentForChange: {},
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
                        populatedPlace: '',
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
                timeForCheckOut: "10:00",
                typeOfApartment: null,
            },
            hideDialog: true,
            filterDataForApartment: {
                typeOfApartment: "",
                selectedAmenities: [],
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
            },
            previewMap: false,
        }
    },
    template: `
    <div id = "styleForApartmentsView">

        <!-- Search, filter, sort for apartments -->
        <form method='post'>

            <input type="text" id="cityID" v-model="searchField.populatedPlace" placeholder="City..." >
            <button type="button" @click="previewMapForSearch()"> Choose on map </button>
            
            <br><br>
            
            <div id="mapSearch" class="mapSearch" v-if="previewMap">
                <br><br>
            </div>


            <input type="text" v-model="searchField.minPrice" placeholder="Min price..." >
            <input type="text" v-model="searchField.maxPrice" placeholder="Max price..." >
            <br><br>

            <input type="text" v-model="searchField.minNumberOfRooms" placeholder="Min rooms..." >
            <input type="text" v-model="searchField.maxNumberOfRooms" placeholder="Max rooms..." >
            <br><br>

            <input type="text" v-model="searchField.minNumberOfGuests" placeholder="Min guests..." >
            <input type="text" v-model="searchField.maxNumberOfGuests" placeholder="Max guests..." >
            <br><br>


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

            <br><br>
            <button type="button" @click="sortAsc">SORT ASC</button>
            <button type="button" @click="sortDesc">SORT DESC</button>

        </form>
        <!-- End of search, filter, sort for apartments -->
        <br>


        <ul>
            <li v-for="apartment in filteredApartments">
                <h2> Type of apartment: {{ apartment.typeOfApartment }} </h2>
                <h2> Price per night: {{ apartment.pricePerNight}} </h2>
                <h2> ID of apartment: {{apartment.id }}  </h2>

                <button type="button" @click="changeApartment(apartment)"> Change </button>
                <button type="button" v-if="apartment.status == 'INACTIVE' " @click="activateApartment(apartment)"> Activate </button>
                <button type="button" v-if=" apartment.logicalDeleted == '0' " @click="deleteApartment(apartment)"> Delete </button>
            
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

                    <!-- Choose image of apartment -->
                    <input type="file" onchange="encodeImageFileAsURLForChanging(this)" />
                    <!-- End of choose of image of apartment -->

                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

                <!--
                     I need this to store image when someone choose and upload to site,
                     and after that i can get src from other methods.

                     And it's hidden because it's ugly to preview in change mood.
                 -->
                <img hidden id="imgForChangeID"  src="" alt="Image of apartment" width="11" height="11">

            </div>
        </div> <!-- End of modal dialog section -->

    </div>

    `,
    methods: {
        initForMap: function () {

            const mapSearch = new ol.Map({
                target: 'mapSearch',
                layers: [
                    new ol.layer.Tile({
                        source: new ol.source.OSM()
                    })
                ],
                view: new ol.View({
                    center: [0, 0],
                    zoom: 2
                })
            })

            mapSearch.on('click', function (evt) {
                console.log(evt.coordinate);
                //alert(evt.coordinate);

                var coord = ol.proj.toLonLat(evt.coordinate);
                reverseGeocode(coord);

            })

        },
        previewMapForSearch: function () {
            this.previewMap = !this.previewMap;
            if (this.previewMap) {
                // Draw map on screen
                this.$nextTick(function () {
                    this.initForMap();
                })
            }
        },
        activateApartment(apartment) {
            axios
                .post('rest/apartments/adminActivationApartment', {
                    addedApartment: apartment
                })
                .then(response => {
                    this.apartments = [];
                    response.data.forEach(el => {
                        if (el.status == "ACTIVE" || el.status == "INACTIVE")
                            this.apartments.push(el);
                    });
                    toastr["success"]("You make success activation !!", "Success activation!");
                    return this.apartments;
                });
        },
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
        onchangeAmenities: function () {
            if (this.filterDataForApartment.selectedAmenities == "") {
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
        changeApartment: function (apartment) {
            this.hideDialog = !this.hideDialog;

            this.newApartment = apartment;

        },
        confirmChanging: function () {

            // Check is empty field input
            // ref: https://stackoverflow.com/questions/5515310/is-there-a-standard-function-to-check-for-null-undefined-or-blank-variables-in
            if (!this.newApartment.id || !this.newApartment.timeForCheckIn || !this.newApartment.timeForCheckOut
                || !this.newApartment.pricePerNight || !this.newApartment.numberOfRooms || !this.newApartment.numberOfGuests || !this.startDateForHost || !this.endDateForHost) {
                toastr["warning"]("All field is required", "Watch out !");
                return;

            }
            // Get Base64 format of image 
            this.newApartment.imagesPath = document.getElementById("imgForChangeID").src;

            axios
                .post('rest/apartments/changeApartment', {
                    addedApartment: this.newApartment,
                    "startDateForReservation": this.startDateForHost,
                    "endDateForReservation": this.endDateForHost
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
                        "hostID": this.apartmentForChange.hostID,
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
        // Draw map on screen
        this.$nextTick(function () {
            this.initForMap();
        })

        axios
            .get('rest/amenities/getAmenities')
            .then(response => {
                this.amenities = [];
                response.data.forEach(el => {
                    this.amenities.push(el);
                });
                return this.amenities;
            });

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
    },
    computed: {
        filteredApartments: function () {
            return this.apartments.filter((apartment) => {
                return this.isMatchSearch(apartment);
            });
        }
    },
})

/**
 * From coords get real address and put that value in form. 
 * @param coords cords (x,y)
 */
function reverseGeocode(coords) {
    fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
        .then(function (response) {
            return response.json();
        }).then(function (json) {

            // TOWN 
            console.log(json.address);
            if (json.address.city) {
                let el = document.getElementById("cityID");
                /*
                    I need this new Event because.
                    The idea is that when I change cityID, i need to update 
                    data in Vue, and only way i found is this.

                    author: Vaxi

                    ref: https://stackoverflow.com/questions/56348513/how-to-change-v-model-value-from-js
                */
                el.value = json.address.city;
                el.dispatchEvent(new Event('input'));
            } else if (json.address.city_district) {
                let el = document.getElementById("cityID");
                el.value = json.address.city_district;
                el.dispatchEvent(new Event('input'));
            }

        });
}