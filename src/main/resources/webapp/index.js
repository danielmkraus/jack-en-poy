const RESULT_LABELS = {
    WIN: 'Player 1 wins',
    LOSE: 'Player 2 wins',
    DRAW: 'Draw'
};

const app = new Vue({
    el: '#app',
    data() {
        return getInitialState();
    },
    methods: {
        play: function () {
            axios
                .post('/api/v1/jack-en-poy/' + this.id)
                .then(response => (this.info = response))
        },
        reset: function () {
            const {info, id} = getInitialState();
            this.info = info;
            this.id = id;
        }
    },
    mounted() {
    }
});

function generateId() {
    return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
        (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    );
}

function getInitialState() {
    return {
        info: {
            data: []
        },
        id: generateId(),
        labels: {
            results: RESULT_LABELS
        }
    }
}
