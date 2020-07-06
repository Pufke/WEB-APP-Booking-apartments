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


Vue.component("host-profile",{
    data(){
        return {
            user: {},
            changedUser: {
                password: '',
                name: '',
                surname: '',
            },
        }
    },
    template:`
    <div id = "styleForProfile">
        <h1> Hello {{user.userName}}, this is your profile. </h1>
        
        <table class="tableInProfil">
            <tr>
                <th> Label </th>
                <th> Current </th>
                <th> New </th>
            </tr>

            <tr>
                <td> Password </td>
                <td>  {{user.password}} </td>
                <td> <input type="text" v-model="changedUser.password" placeholder="Password"> </td>
            </tr>

            <tr>
                <td> Name </td>
                <td> {{user.name}} </td>
                <td> <input type="text" v-model="changedUser.name" placeholder="Name"> </td>
            </tr>

            <tr>
                <td> Surname </td>
                <td> {{user.surname}} </td>
                <td> <input type="text" v-model="changedUser.surname" placeholder="Surname"> </td>
            </tr>

        </table>

        <br><br>
        <button @click="saveChanges()" class="saveChanges" ><i class="fa fa-check" aria-hidden="true"></i> Save changes </button>

    </div>
    `,
    methods: {
        saveChanges:function(){
            axios
            .post('rest/edit/saveUserChanges',{
                "username": this.user.userName,
                "password": this.changedUser.password,
                "name":  this.changedUser.name,
                "surname": this.changedUser.surname,
                "role": this.user.role
            
            })
            .then(response =>{
                toastr["success"]("Success changes!!", "Success!");
            })
            .catch(err => {
                toastr["error"]("Failed during changes :(", "Fail");
            })
        }
    },
    mounted () {
        axios
        .get('rest/edit/profileUser')
        .then( response => this.user = response.data)
    },
    

});