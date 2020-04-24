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

Vue.component("app-login",{
    data() {
        return {
            users: {},
            newUser: {},
            errors: [],
            message: 'poruka'
        }
    },
    template:`
    <div class ="forica">

       
        <form id='login-form' action="../../Apartments/dashboard.html" method='post'>

            <input type="text" placeholder="Username" required>
            <input type="password" placeholder="Password" required>
            
            <button type='submit'>Login</button>

        </form>

        <table border="1">
		<tr bgcolor="lightgrey">
			<th>user name </th><th>Password</th></tr>
			<tr v-for="user in users">
                <td> {{user.userName}}</td>
                <td> {{user.password}}</td>
			</tr>
        </table>

        <h1> {{newUser.userName}} </h1>
        <h1> {{newUser.password}} </h1>
        <h1> {{newUser.name}} </h1>
        <h1> {{newUser.surname}} </h1>
        <h1> {{this.message}} </h1>
        

    </div>
    
    `,
    methods: {
        addNewUser: function(_newUser){
            axios
            .post('rest/users/registration',{"username":''+ _newUser.userName, "password":''+_newUser.password, "name":''+_newUser.name, "surname":''+_newUser.surname})
            .then((response)=>{
                alert('ok');
                toastr["error"]("Nesto nije okej", "FAIL");
                this.newUser.userName = "NEMAAAAAAAAAAAAAAAAAAAAA";
                alert('jos jedan');
            })
            .catch(err => (console.log(error)))
        },//toast('Successfuly register dear '+ _newUser.userName)
        chechRegistration: function(event){

            if (this.newUser.userName && this.newUser.password && this.newUser.name && this.newUser.surname) {
                return true;
            }
            

            /**
             * Save errors, and make notification from him.
             */
            this.errors = [];
            

            /**
             * VALIDATION for frontend !
             * TODO: Make better validation!
             */
            if (!this.newUser.userName) {
                this.errors.push('Field user name is required.');
            }

            if (!this.newUser.password) {
                this.errors.push('Field password is required.');
            }

            if (!this.newUser.name) {
                this.errors.push('Field name is required.');
            }

            if (!this.newUser.surname) {
                this.errors.push('Field surname is required.');
            } 
            
            // TODO OVDE DODATI kod da probam odavde slat zahtev

            if (!this.errors.length) {
                return true;
            }

            

            /**
             * For each error, push notification to user, to inform him about it.
             */
            this.errors.forEach(element => {
                console.log(element)
                toastr["error"](element, "Fail")
            });
             
            
             alert('pre preventa');
            /* Prevent submit if we have errors ! */
            event.preventDefault();
            alert('posle preventa');
        }
        
    },
    mounted() {
        axios.get('rest/users/getJustUsers').then(response => (this.users = response.data));
    },

});
