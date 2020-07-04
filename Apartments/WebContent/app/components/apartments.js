Vue.component("view-apartments", {
    data() {
        return {
            apartments: [],
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


        </form>
        <!-- End of search, filter, sort for apartments -->
        <br>


        <ul>
            <li v-for="apartment in filteredApartments">
                <h2> Type of Appartment: {{ apartment.typeOfApartment }} </h2>
        
                <h2> Price per night: {{ apartment.pricePerNight}} </h2>
                <h2> Number of rooms: {{ apartment.numberOfRooms}} </h2>
                <h2> Number of guests: {{ apartment.numberOfGuests}} </h2>
                <h2> Price per night: {{ apartment.pricePerNight}} </h2>
                <h2> Populated place: {{ apartment.location.address.populatedPlace}} </h2>
                <h2> Time For CheckIn: {{ apartment.timeForCheckIn}} </h2>
                <h2> Time For CheckOut: {{ apartment.timeForCheckOut}} </h2>
            </li>
        </ul>
        
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
            .get('rest/apartments/getApartments')
            .then(response => {
                this.apartments = [];
                response.data.forEach(el => {
                    if (el.status == "ACTIVE" && el.logicalDeleted == 0)
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