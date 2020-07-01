Vue.component("host-mapChoose", {
    data() {
        return {
            amenities: [],          // need it for adding form of new apartment ( we need available amenities )
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
        }
    },
    template: `
    <div>
        <h1> Cao od mape </h1>

        <!-- Form for adding apartment -->
        <form method='post'>
            <input type="text" v-model="newApartment.typeOfApartment" placeholder="Type of apartment..."><br><br>
                    
            <input  name="checkIn" type="time" v-model="newApartment.timeForCheckIn" placeholder="Check in..."><br><br>
            <input name="checkOut"  type="time" v-model="newApartment.timeForCheckOut" placeholder="Check out..."><br><br>
        
            <input  type="number" v-model="newApartment.pricePerNight" placeholder="Price per night..." > <br><br>
            <input  type="number" v-model="newApartment.numberOfRooms" placeholder="Number of rooms ..." > <br><br>
            <input  type="number" v-model="newApartment.numberOfGuests" placeholder="Max guests in room..." > <br><br>


            <!-- Address -->
            <input type="text" id="townID" placeholder="Town name ..."> <br><br>
            <input type="text" id="streetID" placeholder="Street ..."> <br><br>
            <input type="text" id="numberID" placeholder="Number ..."> <br><br>
            <!-- End of address -->

            <!-- List of amenities in apartments -->
            <select v-model="newApartment.apartmentAmentitiesIDs" multiple>
                <option v-for="option in amenities" v-bind:value="option.id">
                    {{ option.itemName }}
                </option>
            </select>
            <!-- End list of amenities in apartments -->

            <button type="button" @click="confirmAdding()">Confirm</button> <br><br>

        </form>
        <!-- End of form for adding apartment -->

        <br><br>
        <button type="button" @click="endAddingApartments()"> End adding apartments </button>

        <br><br><br>
        <div id="map" class="map"> </div>
        

    </div>
    `,
    methods: {
        confirmAdding: function () {
            // Get data from form
            this.newApartment.location.address.populatedPlace = document.getElementById("townID").value;
            this.newApartment.location.address.street = document.getElementById("streetID").value;
            this.newApartment.location.address.number = document.getElementById("numberID").value;


            // TODO: Check for empty fields
            axios
                .post('rest/apartments/addNewApartments', {
                    addedApartment: this.newApartment
                })
                .then(response => {
                    toastr["success"]("You make success adding !!", "Success adding!");
                    return this.apartments;
                });

        },
        endAddingApartments: function () {

            window.location.href = "http://localhost:8080/Apartments/hostDashboard.html#/hostActiveApartments";

        },

        reverseGeocode: function (coords) {
            fetch('http://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
                .then(function (response) {
                    return response.json();
                }).then(function (json) {
                    // TOWN 
                    console.log(json.address);
                    if (json.address.city) {
                        document.getElementById("townID").value = json.address.city;
                    } else if (json.address.city_district) {
                        document.getElementById("townID").value = json.address.city_district;
                    }

                    // STREET
                    if (json.address.road) {
                        document.getElementById("streetID").value = json.address.road;
                    }

                    // NUMBER OF HOUSE
                    if (json.address.house_number) {
                        document.getElementById("numberID").value = json.address.house_number;
                    }

                });
        },
        initForMap: function () {

            const map = new ol.Map({
                target: 'map',
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

            map.on('click', function (evt) {
                console.log(evt.coordinate);
                //alert(evt.coordinate);

                var coord = ol.proj.toLonLat(evt.coordinate);
                reverseGeocode(coord);

            })

        },



    },
    mounted() {
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


    },
    beforeMount() {
        this.initForMap();
    },


});

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
                document.getElementById("townID").value = json.address.city;
            } else if (json.address.city_district) {
                document.getElementById("townID").value = json.address.city_district;
            }

            // STREET
            if (json.address.road) {
                document.getElementById("streetID").value = json.address.road;
            }

            // NUMBER OF HOUSE
            if (json.address.house_number) {
                document.getElementById("numberID").value = json.address.house_number;
            }

        });
}