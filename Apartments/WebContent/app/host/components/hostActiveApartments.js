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

Vue.component("host-ActiveApartments", {
    data() {
        return {
            apartments: [],
            amenities: [],          // need it for adding form of new apartment ( we need available amenities )
            apartmentForChange: {},
            hideDialog: true,
            hideAddDialog: true,
            newItemName: "",
            newApartment: {
                amentities: null,
                comments: null,
                datesForHosting: null,
                host: null,
                identificator: 10000,
                images: null,
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
                numberOfGuests: null,
                numberOfRooms: null,
                pricePerNight: null,
                reservedApartmentList: [],
                reservedStatus: "Nije rezervisano",
                status: "INACTIVE",
                timeForCheckIn: null,
                timeForCheckOut: null,
                typeOfApartment: null,
            },
            selected: [] // ovo sam uzeo samo privremeno dok ne sredimo u apartmanu listu sadrzaja koju on ima

        }
    },

    template: `
    <div id = "styleForApartmentsView">

        <br>
        <button type="button" @click="addItem()"> Add new item </button>

        <!-- Cards for apartments -->
        <ul>
            <li v-for="apartment in apartments">

                <h2> {{ apartment.typeOfApartment }} </h2>
                <h2> {{ apartment.pricePerNight}} </h2>

                <button type="button" @click="changeApartment(apartment)"> Change </button>
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

        <!-- Modal DIALOG section for ADDING -->
        <div id = "addDialogForApartments" v-bind:class="{bgModal: hideAddDialog, bgModalShow: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>
                <!-- <img src="http://icons.iconarchive.com/icons/cjdowner/cryptocurrency-flat/128/ICON-ICX-icon.png" alt=""> -->


                <form method='post'>
                    <input type="text" v-model="newApartment.typeOfApartment" placeholder="Type of apartment...">
                    <input  type="date" v-model="newApartment.timeForCheckIn" placeholder="Check in...">
                    <input  type="date" v-model="newApartment.timeForCheckOut" placeholder="Check out...">
                    <input  type="number" v-model="newApartment.pricePerNight" placeholder="Price per night..." >
                    <input  type="number" v-model="newApartment.numberOfRooms" placeholder="Number of rooms ..." >
                    <input  type="number" v-model="newApartment.numberOfGuests" placeholder="Max guests in room..." >

                    <!-- Address -->
                    <input type="text" v-model="newApartment.location.address.populatedPlace" placeholder="Town name ...">
                    <input type="text" v-model="newApartment.location.address.street" placeholder="Street ...">
                    <input type="text" v-model="newApartment.location.address.number" placeholder="Number ...">
                    <!-- End of address -->

                    <!-- List of amenities in apartments -->
                    <select v-model="selected" multiple>
                        <option v-for="option in amenities" v-bind:value="option.name">
                            {{ option.name }}
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

    /*
            location:{
                address:{
                    number:null,
                    populatedPlace:null,
                    street:null,
    */
    //tip apartmana, broj soba, broj gostiju, lokaciju, itd) 
    //Napomena: Postoji pregled svih dostupnih sadržaja apartmana koji se mogu dodeliti apartmanu
    methods: {
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
                .post('rest/apartments/changeMyApartment', {
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
                    addedApartment: this.newApartment
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

            alert("potvrda");
        },
    },
    mounted() {
        axios
            .get('rest/apartments/getMyApartments')
            .then(response => {
                response.data.forEach(el => {
                    if (el.status == "ACTIVE")
                        this.apartments.push(el);
                });
                return this.apartments;
            });

        axios
            .get('rest/amenities/getAmenities')
            .then(response => {
                response.data.forEach(el => {
                    this.amenities.push(el);
                });
                return this.amenities;
            });
    }


});