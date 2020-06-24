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
            apartmentForChange: {},
            hideDialog: true
        }
    },

    template: `
    <div id = "styleForApartmentsView">

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

                    
                    <input type="date" v-model="apartmentForChange.timeForCheckIn" placeholder="Check in...">
                    <input type="date" v-model="apartmentForChange.timeForCheckOut" placeholder="Check out...">
                    <input type="number" v-model="apartmentForChange.pricePerNight" placeholder="Price per night..." >
                    <input type="number" v-model="apartmentForChange.numberOfRooms" placeholder="Number of rooms ..." >
                    <input type="number" v-model="apartmentForChange.numberOfGuests" placeholder="Max guests in room..." >

                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal dialog section -->

    </div>
    
    `,
    methods: {
        changeApartment: function (apartment) {
            this.hideDialog = !this.hideDialog;

            this.apartmentForChange = apartment;

        },
        confirmChanging: function () {
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
            })
    }


});