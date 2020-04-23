Vue.component("app-register",{
    data() {
        return {
            users: null,
            newUser: {}
        }
    },
    template:`
    <div class ="forica">
        <h1> Cao iz registera! !!!</h1>

        <input type='checkbox' id='form-switch'>
        <form id='login-form' action="" method='post'>

            <input type="text" placeholder="Username" required>
            <input type="password" placeholder="Password" required>
            
            <button type='submit'>Login</button>


            <label for='form-switch'><span>Register</span></label>
        </form>

        <form id='register-form' action="" method='post'>

            <input type="text" v-model="newUser.userName" placeholder="Username" required>
            <input type="text" v-model="newUser.name" placeholder="Name" required>
            <input type="text" v-model="newUser.surname" placeholder="Surname" required>
            <input type="password" v-model="newUser.password" placeholder="Password" required>
            <input type="password" placeholder="Re Password" required>

            <button type='submit' v-on:click="addNewUser(newUser)" >Register</button>

            <label for='form-switch'>Already Member ? Sign In Now..</label>
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
       
        

    </div>
    
    `,
    methods: {
        addNewUser: function(_newUser){
            axios
            .post('rest/users/registration',{"username":''+ _newUser.userName, "password":''+_newUser.password, "name":''+_newUser.name, "surname":''+_newUser.surname})
            .then(response=>(toast('Successfuly register dear '+ _newUser.userName)))
        }
        
    },
    mounted() {
        axios.get('rest/users/getNewUser').then(response => (this.newUser = response.data));
        axios.get('rest/users/getJustUsers').then(response => (this.users = response.data));
    },

});