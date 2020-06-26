Vue.component("host-CommentsAndReviews", {
    data() {
        return {
            comments: []
        }
    },
    template: `
    <div id = "styleForApartmentsView" >
        <h1> Hello from comments </h1>

        <ul>
            <li v-for="comment in comments">
                <h2> Author ID: {{ comment.guestAuthorOfCommentID}} </h2>
                <h2> Apartment ID: {{ comment.commentForApartmentID }} </h2>
                <h2> Text: {{ comment.txtOfComment }} </h2>
                <h2> Rating: {{ comment.ratingForApartment }} </h2>

                <button v-if="comment.isAvailableToSee" type="button" @click="hideComment(comment)"> Hide </button>
                <button v-if="!comment.isAvailableToSee" type="button" @click="showComment(comment)"> Show </button>
            </li>
        </ul>

        <br>
        <table border="1">
            <tr bgcolor="lightgrey">
                <th> Text </th>
                <th> Rating </th>
            </tr>
            <tr v-for="comment in comments">
                <td> {{ comment.txtOfComment }} </td>
                <td> {{ comment.ratingForApartment }} </td>
            </tr>
        </table>

    </div>
    `,
    methods: {
        hideComment: function (commentParam) {
            axios
                .post('rest/comments/hideComment', {
                    comment: commentParam
                })
                .then(response => {
                    this.comments = [];
                    response.data.forEach(el => {
                        this.comments.push(el);
                    });
                    toastr["success"]("You make success hide of comment !!", "Success hiding!");

                    return this.comments;
                });
        },
        showComment: function (commentParam) {
            axios
                .post('rest/comments/showComment', {
                    comment: commentParam
                })
                .then(response => {
                    this.comments = [];
                    response.data.forEach(el => {
                        this.comments.push(el);
                    });
                    toastr["success"]("You make success show of comment !!", "Success showing!");

                    return this.comments;
                });
        }
    },
    mounted() {
        axios
            .get('rest/comments/getMyComments')
            .then(response => {
                this.comments = [];
                response.data.forEach(el => {
                    this.comments.push(el);
                });
                return this.comments;
            });
    },
});