Vue.component("guest-apartments", {
    data() {
        return {
            startDateForReservation: null,
            freeDates: [],
            commentForHost: "",
            numberOfNights: "",
            apartments: [],
            amenities: [],  
            comments: [],
            user: {},
            filterDataForApartment: {
                typeOfApartment: "",
                selectedAmenities: []
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
                <h2> ID : {{ apartment.id }} </h2>
                <h2> {{ apartment.typeOfApartment }} </h2>
                <h2> {{ apartment.pricePerNight}} </h2>
                <h2> ADRESA: </h2> 
                <h3> Mesto: {{ apartment.location.address.populatedPlace }} </h3>
                <h3> Ulica: {{ apartment.location.address.street }} </h3>
                <h3> Broj: {{ apartment.location.address.number }} </h3>
                <h3> ZIP code: {{ apartment.location.address.zipCode }} </h3>
                
                <h2> Lokacija: </h2> 
                <h3> Geografska duzina: {{ apartment.location.longitude }} </h3>
                <h3> Geografska sirina: {{ apartment.location.latitude }} </h3>
                
                <label for="fromDate">Pocetni datum rezervacije:</label>
                <input type="date" v-model="startDateForReservation" id="fromDate" name="fromDate">
                <input type="number" v-model="numberOfNights" placeholder="Number of nights..." >
                
                 <input type="text" v-model="commentForHost" placeholder="Comment for host..." >
                 
                <button @click="makeReseervation2(apartment.id)">MAKE RESERVATION</button>
                
                <button @click="viewComments(apartment.id)">VIEW COMMENTS</button>
                
                 <button @click="viewFreeDates(apartment.id)">VIEW FREE DATES</button>
                <br>
                
    			
                
            </li>
        </ul>
         <table border="1">
        			<tr bgcolor="lightgrey"><th> Free dates for choosen apartment </th> </tr>
    				<tr v-for="date in freeDates">
    					<td> {{ date }} </td>
            		</tr>
        </table>
        
         <table border="1">
        	<tr bgcolor="lightgrey">
        <th> ID </th> <th> Comment </th> <th> Rating for apartment </th><th> Author </th></tr>
            <tr v-for="comment in comments">
                <td> {{ comment.id }} </td>
                <td> {{ comment.txtOfComment }} </td>
                <td> {{ comment.ratingForApartment }} </td>
    		    <td> {{ comment.guestAuthorOfCommentID }} </td>
            </tr>
        </table>
        
        
        <br>
        <table border="1">
        <tr bgcolor="lightgrey">
        <th> ID </th> <th> Status </th><th> Type </th><th> Price </th><th> Rooms </th><th> Guests</th><th> Check in</th><th> Check out</th><th>Location</th> </tr>
            <tr v-for="apartment in filteredApartments">
                <td> {{ apartment.id }} </td>
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
                    .get('rest/apartments/getApartments')
                    .then(response => {
                        this.apartments = [];
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
                    x = helper.string.to_ascii(a[columns[index]].toLowerCase(), -1),
                        y = helper.string.to_ascii(b[columns[index]].toLowerCase(), -1);
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
        makeReseervation2: function (identificator) {
            if (!this.startDateForReservation || !this.numberOfNights) {
                toastr["warning"]("All field is required", "Watch out !");
                return;

            }
            axios
                .post('rest/reservation/makeReservations', {
                    "apartmentIdentificator": identificator,
                    "dateOfReservation": this.startDateForReservation,
                    "numberOfNights": this.numberOfNights,
                    "messageForHost": this.commentForHost,
                    "guestID": this.user.id,
                    "statusOfReservation": "KREIRANA",
                })
                .then(response => {
                    filteredApartments = [];
                    this.startDateForReservation = null;
                    this.numberOfNights = "";
                    this.apartments.forEach(el => {
                        if (el.identificator != identificator) {
                            filteredApartments.push(el);
                        }
                    });
                    this.apartments = filteredApartments;
                    toastr["success"]("Apartment is reserved! ", "Success!");
                })
                .catch(err => {
                    toastr["error"]("Apartment is not free in that data interval!", "Fail");
                })
        },
        viewFreeDates: function (id) {
            console.log(id);
            axios
                .post('rest/apartments/getApartmentFreeDates', {
                    "apartmentID": id
                })
                .then(response => {
                    this.freeDates = [];
                    response.data.forEach(el => {
                        this.freeDates.push(el);
                    });
                    return this.freeDates;
                });


        },
        viewComments: function (apartmentCommentsIDs) {
            console.log(apartmentCommentsIDs);
            axios
                .post('rest/comments/getCommentsForApartment', {
                    "apartmentID": apartmentCommentsIDs
                })
                .then(response => {
                    this.comments = [];
                    response.data.forEach(el => {
                        if (el.isAvailableToSee == "1")
                            this.comments.push(el);
                    });
                    return this.comments;
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
        },

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

        let one = 'rest/apartments/getApartments';
        let two = 'rest/edit/profileUser';

        let requestOne = axios.get(one);
        let requestTwo = axios.get(two);

        axios.all([requestOne, requestTwo]).then(axios.spread((...responses) => {
            //  this.apartments = responses[0].data;
            responses[0].data.forEach(el => {
                if (el.status == "ACTIVE") {
                    this.apartments.push(el);
                }
            });
            this.user = responses[1].data;
        })).catch(errors => {
            console.log("Greska brt");
        })
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