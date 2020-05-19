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

Vue.component("administrator-contents",{
    data() {
        return {
            amenities: [],
            hideDialog: true,
            itemForChange: {
                amenitiesID: 999,
                name : ""
            },
            hideAddDialog: true,
            newItemName: ""
        }
    },
    template:`
    <div id = "styleForApartmentsView" >
        <h1> List of content in apartments </h1>

        <br>
        <button type="button" @click="addItem()"> Add new item </button>


        <!-- Modal dialog section for adding -->
        <div id = "addDialogForAmenities" v-bind:class="{bgModal: hideAddDialog, bgModalShow: !hideAddDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideAddDialog = !hideAddDialog">+</div>
                <img src="http://icons.iconarchive.com/icons/cjdowner/cryptocurrency-flat/128/ICON-ICX-icon.png" alt="">


                <form method='post'>
                    <input type="text" v-model="newItemName" placeholder="Name of amenities...">
                    
                    <button type="button" @click="confirmAdding">Confirm</button>
                    <button type="button" @click="hideAddDialog = !hideAddDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal adding dialog section -->


        <ul>
            <li v-for="item in amenities">
                <h2> {{ item.amenitiesID }} </h2>
                <h2> {{ item.name }} </h2>

                <button type="button" @click="changeItem(item)"> Change </button>
                <button type="button" @click="deleteItem(item)"> Delete </button>
            

            </li>
        </ul>

        <!-- Modal dialog section for changing -->
        <div id = "dialogForAmenities" v-bind:class="{bgModal: hideDialog, bgModalShow: !hideDialog}">
            <div class="modal-contents">
        
                <div class="close" @click="hideDialog = !hideDialog">+</div>
                <img src="http://icons.iconarchive.com/icons/cjdowner/cryptocurrency-flat/128/ICON-ICX-icon.png" alt="">


                <form method='post'>
                    <input type="text" v-model="itemForChange.name" placeholder="Name of amenities...">
                    
                    <button type="button" @click="confirmChanging">Confirm</button>
                    <button type="button" @click="hideDialog = !hideDialog">Cancel</button>

                </form>

            </div>
        </div> <!-- End of modal dialog section -->


        <br>
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> ID </th>
                <th> name </th> 
            </tr>
            <tr v-for="item in amenities">
                <td> {{ item.amenitiesID }} </td>
                <td> {{ item.name}} </td>

            </tr>
        </table>

    </div>

    `,
    methods: {
        addItem: function(){
            this.hideAddDialog = !this.hideAddDialog;
        },
        confirmAdding: function(){
            
            axios
            .post('rest/amenities/addItem',{
                "newItemName": this.newItemName
            })
            .then( response =>{
                this.amenities = [];
                response.data.forEach(el => {
                    this.amenities.push(el);
            });
            toastr["success"]("You make success adding !!", "Success adding!");
            return this.amenities;
            });

        },
        changeItem: function(item){
            this.hideDialog = !this.hideDialog;

            this.itemForChange = item;

        },
        confirmChanging: function(){
            
            axios
            .post('rest/amenities/changeItem',{
                "amenitiesID": this.itemForChange.amenitiesID,
                "name": this.itemForChange.name
            })
            .then( response =>{
                this.amenities = [];
                response.data.forEach(el => {
                    this.amenities.push(el);
            });
            toastr["success"]("You make success change !!", "Success changes!");
            return this.amenities;
            });

        },
        deleteItem: function(item){
            this.itemForChange = item;
            this.requestForDeleteItem();
        },
        requestForDeleteItem: function(){

            axios
            .delete('rest/amenities/deleteItem',{
                data:{
                    "amenitiesID": this.itemForChange.amenitiesID,
                    "name": this.itemForChange.name
                }
                
            })
            .then( response =>{
                this.amenities = [];
                response.data.forEach(el => {
                    this.amenities.push(el);
            });
            toastr["success"]("You make success delete !!", "Success delete!");
            return this.amenities;
            });

        }
    },
    mounted() {
        axios
        .get('rest/amenities/getAmenities')
        .then( response => {
        	response.data.forEach(el => {
        		this.amenities.push(el);
        	});
        	return this.amenities;
        });
    },
});