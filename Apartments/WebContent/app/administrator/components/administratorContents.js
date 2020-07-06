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

Vue.component("administrator-contents", {
    data() {
        return {
            amenities: [],
            hideDialog: true,
            itemForChange: {
                amenitiesID: "",
                name: ""
            },
            hideAddDialog: true,
            newItemName: ""
        }
    },
    template: `
    <div id = "styleForApartmentsView" >
        <h1> List of content in apartments </h1>

        <br>
        <button type="button" @click="addItem()" class="btn"><i class="fa fa-plus" aria-hidden="true"></i> ADD NEW </button> <br><br>


        <!-- Modal dialog section for adding -->
        <div id = "addDialogForAmenities" v-bind:class="{bgModal: hideAddDialog, bgModalShowAmenities: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>

                <form method='post'>
                    <input type="text" v-model="newItemName" placeholder="Name of amenities...">
                    
                    <button type="button" @click="confirmAdding">Confirm</button>
                    <button type="button" @click="hideAddDialog = !hideAddDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal adding dialog section -->


        <ul>
            <li v-for="item in amenities">

                <h2> {{ item.itemName }} </h2>

                <button type="button" @click="changeItem(item)" class="changeButtonStyle" ><i class="fa fa-pencil" aria-hidden="true"></i> Change </button><br>
                <button type="button" @click="deleteItem(item.id)" class="deleteButtonStyle" ><i class="fa fa-trash" aria-hidden="true"></i> Delete </button>
            

            </li>
        </ul>

        <!-- Modal dialog section for changing -->
        <div id = "dialogForAmenities" v-bind:class="{bgModal: hideDialog, bgModalShowAmenities: !hideDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideDialog = !hideDialog">+</div>

                <form method='post'>
                    <input type="text" v-model="itemForChange.name" placeholder="Name of amenities...">
                    
                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal dialog section -->


        <br>

        <!-- Table of amenities in apartments -->
        <div class="styleForTable">
            <table style="width:100%">

                <thead>
                    <tr>
                        <th> ID </th>
                        <th> name </th> 
                    </tr>
                </thead>

                <tbody>
                    <tr v-for="item in amenities">
                        <td> {{ item.id }} </td>
                        <td> {{ item.itemName}} </td>
                    </tr>
                </tbody>                

            </table>
        </div>
        <!-- End of table for amenities in apartments -->

    </div>

    `,
    methods: {
        addItem: function () {
            this.hideAddDialog = !this.hideAddDialog;
        },
        confirmAdding: function () {

            axios
                .post('rest/amenities/addItem', {
                    "newItemName": this.newItemName
                })
                .then(response => {
                    this.amenities = [];
                    response.data.forEach(el => {
                        this.amenities.push(el);
                    });
                    toastr["success"]("You make successful adding !!", "Successful adding!");
                    return this.amenities;
                });

        },
        changeItem: function (item) {
            this.hideDialog = !this.hideDialog;

            this.itemForChange.amenitiesID = item.id;
            this.itemForChange.name = item.name;

        },
        confirmChanging: function () {

            axios
                .post('rest/amenities/changeItem', {
                    "amenitiesID": this.itemForChange.amenitiesID,
                    "name": this.itemForChange.name
                })
                .then(response => {
                    this.amenities = [];
                    response.data.forEach(el => {
                        this.amenities.push(el);
                    });
                    toastr["success"]("You make successful change !!", "Successful changes!");
                    return this.amenities;
                });

        },
        deleteItem:  function (item) {
            axios
                .delete('rest/amenities/deleteItem', {
                    data: {
                        "amenitiesID": item,
                        "name": item.name
                    }
                })
                .then(response => {
                    this.amenities = [];
                    response.data.forEach(el => {
                        this.amenities.push(el);
                    });
                    toastr["success"]("You make successful delete !!", "Successful  delete!");
                    return this.amenities;
                });

        }
    },
    mounted() {

        axios
            .get('rest/amenities/getAmenities')
            .then(response => {
                response.data.forEach(el => {
                    this.amenities.push(el);
                });
                return this.amenities;
            });

    },
});