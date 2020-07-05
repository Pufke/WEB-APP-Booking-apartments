Vue.component("guest-header",{
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

                <li><router-link to="/" exact> Apartments & rooms </router-link></li>
                <li><router-link to="/reservations" exact> Reservations </router-link></li>
                <li><router-link to="/profile" exact> Profile </router-link></li>
                <li><button @click="logout"> Log out </button></li>
                
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

