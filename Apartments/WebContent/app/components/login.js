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
            newUser: {},
            errors: []
        }
    },
    template:`
    <div class ="forica">

       
        <form id='login-form' @submit="checkLogin" method='post'>

            <input type="text" v-model="newUser.userName" placeholder="Username" required>
            <input type="password" v-model="newUser.password" placeholder="Password" required>
            
            <button type='submit'>Login</button>

        </form>      

    </div>
    
    `,
    methods: {
        checkLogin: function(event){
            /* Prevent submit if we have errors ! */
            event.preventDefault();

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


            if (!this.errors.length) {
                axios
                .post('rest/users/login',{"username":''+ this.newUser.userName, "password":''+this.newUser.password})
                .then(response=>{
                    this.message = response.data;
                    console.log("\n\n ------- PODACI -------\n");
                    console.log(response.data);
                    toastr["success"]("Let's go, travel around world !!", "Success log in!");
                    console.log("\n\n ----------------------\n\n");
                    //TODO 11: Napraviti bolju resenje od ovoga, jer je ovo bas HC redirektovanje na dashboard.
                    /**
                     * Isto kao i TODO 10 problem. 
                     *
                     * author: Vaxi
                     */
                    location.href = response.data; // we get from backend redirection to login with this

                    
                })
                .catch(err =>{ 
                    console.log("\n\n ------- ERROR -------\n");
                    console.log(err);
                    toastr["error"]("Password, username are incorrect, or your account is blocked!", "Fail");
                    console.log("\n\n ----------------------\n\n");
                })
                return true;
            }

            /**
             * For each error, push notification to user, to inform him about it.
             */
            this.errors.forEach(element => {
                console.log(element)
                toastr["error"](element, "Fail")
            });
             
        }
        
    },
    mounted() {
        axios.get('rest/users/getNewUser').then(response => (this.newUser = response.data));
    },
});
