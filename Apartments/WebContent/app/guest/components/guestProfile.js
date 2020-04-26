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


Vue.component("guest-profile",{
    data(){
        return {
            user: {},
        }
    },
    template:`
    <div>
        <h1> Hello from profile </h1>
        <br>
        <h1> user name: {{user.userName}} </h1>

        <h1> password: {{user.password}} </h1>
        <input type="text" v-model="user.password" placeholder="Password">

        <h1> name: {{user.name}} </h1>
        <input type="text" v-model="user.name" placeholder="Name">


        <h1> surname: {{user.surname}} </h1>
        <input type="text" v-model="user.surname" placeholder="Surname">


        <h1> role: {{user.role}} </h1>
        <input type="text" v-model="user.role" placeholder="Role" required>

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
        .get('rest/edit/profileGuest')
        .then( response => this.user = response.data)
    },
    

});