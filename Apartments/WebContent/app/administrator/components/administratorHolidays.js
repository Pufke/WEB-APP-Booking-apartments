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

Vue.component("administrator-holidays", {
    data() {
        return {
            hollidays: [],
            hideDialog: true,
            hideAddDialog: true,
            newDate: null
        }
    },
    template: `
    <div id = "styleForApartmentsView" >
        <h1> Add new Holiday date </h1>

        <br>
        <button type="button" @click="addItem()" class="btn"><i class="fa fa-plus" aria-hidden="true"></i> ADD NEW</button><br><br>


        <!-- Modal dialog section for adding -->
        <div id = "addDialogForAmenities" v-bind:class="{bgModal: hideAddDialog, bgModalShow: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>

                <form method='post'>
                    <input type="date" v-model="newDate" >
                    
                    <button type="button" @click="confirmAdding">Confirm</button>
                    <button type="button" @click="hideAddDialog = !hideAddDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal adding dialog section -->

        <br>
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Holidays </th>
            </tr>
            <tr v-for="holliday in hollidays">
                <td> {{ holliday }} </td>

            </tr>
        </table>

    </div>

    `,
    methods: {
        addItem: function () {
            this.hideAddDialog = !this.hideAddDialog;
        },
        confirmAdding: function () {

            axios
                .post('rest/reservation/addHolliday', {
                    "dateForAdd": this.newDate
                })
                .then(response => {
                    this.hollidays = [];
                    response.data.forEach(el => {
                        this.hollidays.push(el);
                    });
                    toastr["success"]("You make success adding !!", "Success adding!");
                    return this.hollidays;
                });

        },
    },
    mounted() {

        axios
            .get('rest/reservation/getHollidays')
            .then(response => {
                response.data.forEach(el => {
                    this.hollidays.push(el);
                });
                return this.hollidays;
            });

    },
});