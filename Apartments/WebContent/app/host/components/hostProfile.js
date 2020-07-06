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
        }
    },
    template:`
    <div id = "styleForProfile">
        <h1> Profile </h1>
        <br>
        <h2> Username: {{user.userName}} </h2>

        <h2> Password: {{user.password}} </h2>
        <input type="text" v-model="user.password" placeholder="Password">

        <h2> Name: {{user.name}} </h2>
        <input type="text" v-model="user.name" placeholder="Name">


        <h2> Surname: {{user.surname}} </h2>
        <input type="text" v-model="user.surname" placeholder="Surname">


        <br>
        <button @click="saveChanges(user)"> Save changes </button>

    </div>
    `,
    methods: {
        saveChanges:function(_user){
            axios
            .post('rest/edit/saveUserChanges',{
                "username":'' + _user.userName,
                "password":'' + _user.password,
                "name": ''+ _user.name,
                "surname": '' + _user.surname,
                "role": '' + _user.role
            
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