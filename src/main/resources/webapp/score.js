const score = new Vue({
  el: '#score',
  data() {
    return getInitialState();
  },
  methods: {
    getScore: function () {
      axios
      .get('/api/v1/jack-en-poy')
      .then(response => (this.score = response.data))
      .then(setTimeout(() => {
        this.getScore()
      }, 2000));
    }
  },
  mounted() {
    this.getScore();
  }
});

function getInitialState() {
  return {
    score: {
      draw: -1,
      total: -1,
      firstPlayerWin: -1,
      secondPlayerWin: -1
    }
  }
}
