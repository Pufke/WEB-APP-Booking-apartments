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
            images: [],
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
            previewSearch: false,
            previewFilters: false,
            previewSort: false,
        }
    },
    template: `
    <div id = "styleForApartmentsView">

        <button type="button" @click=" previewSearch = !previewSearch " class="btn"><i class="fa fa-search" aria-hidden="true"></i> SEARCH </button>
        <button type="button" @click=" previewFilters = !previewFilters " class="btn"><i class="fa fa-filter" aria-hidden="true"></i> FILTERS </button>
        <button type="button" @click=" previewSort = !previewSort " class="btn"><i class="fa fa-sort" aria-hidden="true"></i> SORT </button>

        <br><br>
        <!-- Search for apartments -->
        <div class="searchFilterSortApartmentsUnregister" v-if="previewSearch" >
            <form method='post'> 

                <input type="text" v-model="searchField.minPrice" v-bind:class="{filledInput: searchField.minPrice != '' }" placeholder="min price..." >
                <input type="text" v-model="searchField.maxPrice" v-bind:class="{filledInput: searchField.maxPrice != '' }" placeholder="max price..." >
                <br><br>

                <input type="text" v-model="searchField.minNumberOfRooms" v-bind:class="{filledInput: searchField.minNumberOfRooms != '' }" placeholder="min rooms..." >
                <input type="text" v-model="searchField.maxNumberOfRooms" v-bind:class="{filledInput: searchField.maxNumberOfRooms != '' }" placeholder="max rooms..." >
                <br><br>

                <input type="text" v-model="searchField.minNumberOfGuests" v-bind:class="{filledInput: searchField.minNumberOfGuests != '' }" placeholder="min guests..." >
                <input type="text" v-model="searchField.maxNumberOfGuests" v-bind:class="{filledInput: searchField.maxNumberOfGuests != '' }" placeholder="max guests..." >
                <br><br>

                <input type="text" id="cityID" v-model="searchField.populatedPlace" v-bind:class="{filledInput: searchField.populatedPlace != '' }" placeholder="city..." >
                <button type="button" @click="previewMapForSearch()"><i class="fa fa-map-marker" aria-hidden="true"></i> Choose on map </button>
            </form>
        </div>
        <!-- End search for apartments -->

        <br><br>
        <!-- Filter for apartments -->
        <div class="filterForAdminApartments" v-if="previewFilters">
            <form method='post' 
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
                <!-- List of all amenities in apartments -->
                <select v-model="filterDataForApartment.selectedAmenities" class="multipleSelectAmenities" multiple @change="onchangeAmenities()">

                    <option value=""> Without filter for amenities </option>
                    <option v-for="option in amenities" v-bind:value="option.id">
                        {{ option.itemName }}
                    </option>

                </select>
                <!-- End list of all amenities in apartments -->

                
                
                <br><br>
            </form>
        </div>
        <!-- End filters for apartments -->

        <br><br>
        <!-- Sort for apartments -->
        <div v-if="previewSort" class="sortInApp">
            <form method='post'>

                <button type="button" @click="sortAsc"><i class="fa fa-sort" aria-hidden="true"></i> PRICE UP</button>
                <button type="button" @click="sortDesc"><i class="fa fa-sort" aria-hidden="true"></i> PRICE DOWN</button>

            </form>
        </div>
        <!-- End sort for apartments -->
        <br><br>

        <!-- Map for search city -->
        <div id="mapSearch" class="mapSearch" v-if="previewMap"></div>

        <!-- Cards for apartments -->
        <ul>
            <li v-for="apartment in filteredApartments">
                <img class="imagesOfApartment" v-bind:src="getImagesPath(apartment)">

                <table class="tableInCards">
                    <tr>
                        <td> Type of apartment: </td>
                        <td> {{ apartment.typeOfApartment }} </td>
                    </tr>

                    <tr>
                        <td> Price per night: </td>
                        <td> {{ apartment.pricePerNight}} $ </td>
                    </tr>
                </table>

                <button type="button" v-if=" apartment.status == 'INACTIVE'  "  @click="activateApartment(apartment)"><i class="fa fa-check-circle" aria-hidden="true"></i> Activate </button> <br>
                <button type="button" v-if=" apartment.logicalDeleted == '0' " @click="changeApartment(apartment)" class="changeButtonStyle" ><i class="fa fa-pencil" aria-hidden="true"></i>  Change </button> <br>
                <button type="button" v-if=" apartment.logicalDeleted == '0' " @click="deleteApartment(apartment)" class="deleteButtonStyle" ><i class="fa fa-trash" aria-hidden="true"></i>  Delete </button> <br>
            
            </li>
        </ul>
        <!-- End of cards for apartments -->
        
        <br>

        <!-- Table of all apartments -->
        <div class="styleForTable">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> Status </th>
                        <th> Type </th>
                        <th> Price </th>
                        <th> Rooms </th>
                        <th> Guests</th>
                        <th> Check in</th>
                        <th> Check out</th>
                        <th>Location</th>
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="apartment in filteredApartments">
                        <td> {{ apartment.status }} </td>
                        <td> {{ apartment.typeOfApartment }} </td>
                        <td> {{ apartment.pricePerNight}} </td>
                        <td> {{ apartment.numberOfRooms}} </td>
                        <td> {{ apartment.numberOfGuests}} </td>
                        <td> {{ apartment.timeForCheckIn}} </td>
                        <td> {{ apartment.timeForCheckOut}} </td>
                        <td> 
                            {{ apartment.location.address.populatedPlace }},
                            {{ apartment.location.address.street }},
                            {{ apartment.location.address.number }}, 
                        </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for all apartments -->

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
        getImagesPath: function (apartment) {
            let imageDefault = "data:image/png;base64,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";

            // Default image
            if (apartment.imagesPath == "withoutPath")
                return imageDefault;

            let retImage;

            // trenutno je u atributu imagesPath ili id ili 'withoutPath'
            // pa ako je id onda ga u int treba prebaciti
            retImage = this.getImageBase64FromId(parseInt(apartment.imagesPath, 10));

            return retImage;

        },
        getImageBase64FromId: function (idOfImage) {
            let base64Image;

            for (let el of this.images) {
                if (el.id == idOfImage) {
                    base64Image = el.code64ForImage;
                    break;
                }
            }
            return base64Image;
        },
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

                    // Seting some extra style for map
                    let c = document.getElementById("mapSearch").childNodes;
                    c[0].style.borderRadius  = '10px';
                    c[0].style.border = '4px solid lightgrey';
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
                    toastr["success"]("You make successful activation !!", "Successful activation!");
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
                    toastr["success"]("You make successful change !!", "Successful  changes!");

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
                    toastr["success"]("You make successful delete !!", "Successful delete!");

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

        axios.get('rest/images/getImages').then(response => (this.images = response.data));

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