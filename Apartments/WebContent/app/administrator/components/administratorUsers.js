Vue.component("administrator-users",{
    data(){
        return{
            users: {},
            searchData:{
                userName: "",
                password: "",
                name: "",
                surname: "",
                role: ""
            }
        }
    },

    template: `
    <div>
        <h1> users part </h1>

        <form method='post'>

            <input type="text" v-model="searchData.userName" placeholder="username..." >
            <input type="text" v-model="searchData.name" placeholder="Name..." >
            <input type="text" v-model="searchData.surname" placeholder="surname..." >
            <input type="text" v-model="searchData.role" placeholder="role..." >

            <button type="button" @click="searchParam" >Search</button>
            <button type="button" @click="cancelSearch">Cancel search</button>
            

        </form>
        <br>


        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Username </th>
                <th> Password</th>
                <th> Name </th>
                <th> Surname </th>
                <th> Role </th>
            </tr>

            <tr v-for="user in users">
                <td> {{user.userName}}</td>
                <td> {{user.password}}</td>
                <td> {{user.name}} </td>
                <td> {{ user.surname }} </td>
                <td> {{ user.role }} </td>
			</tr>
        
        </table>

    </div>
    `,
    methods: {
        searchParam: function(event){
            event.preventDefault();
            
            axios
            .post('rest/users/getSearchedUsers',{
                "username": '' + this.searchData.userName,
                "password": '' + this.searchData.password,
                "name": '' + this.searchData.name,
                "surname": '' + this.searchData.surname,
                "role": '' + this.searchData.role
            })
            .then(response =>{
                this.users = response.data;
            })
        },
        cancelSearch: function(){
            this.searchData.userName = "";
            this.searchData.name = "";
            this.searchData.surname = "";
            this.searchData.role = "";

            axios.get('rest/users/getJustUsers').then(response => (this.users = response.data));
        }

    },
    mounted() {
        axios.get('rest/users/getJustUsers').then(response => (this.users = response.data));
    },
});