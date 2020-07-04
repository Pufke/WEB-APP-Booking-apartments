Vue.component("administrator-header",{
    data: function(){
        return{
            fleg:0
        }
    }
    ,
    template: `
    <div id="divHeaderGuest">
        <nav>
            <ul>
                <li><router-link to="/home" exact>Home Page</router-link></li>
                <li><router-link to="/profile" exact>Profile</router-link></li>
                <li><router-link to="/users" exact>Users</router-link></li>
                <li><router-link to="/apartments" exact> Apartments </router-link></li>
                <li><router-link to="/contents" exact> Contents of apartments </router-link></li>
                <li><router-link to="/reservations" exact> Reservations </router-link></li>
                <li><router-link to="/comments" exact> Comments and reviews </router-link></li>
                <li><router-link to="/holidays" exact> Add Holidays </router-link></li>
                <li><button @click="logout" > Log out </button></li>
                
            </ul>
        </nav>

    </div>		  
    
    `,
    methods: {
        logout: function(event){
            event.preventDefault
            axios
            .get('rest/logout/someone')
            .then(response => {
                location.href = "/Apartments/#/login";
            })
            .catch(err => {
                console.log(err);
                alert('Error during log out');
            })
        }
    },
});

