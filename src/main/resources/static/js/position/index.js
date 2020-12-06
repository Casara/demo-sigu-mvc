new Vue({
  el: '#main',
  data() {
    return {
      isBusy: false,
      fields: [
        {
          key: 'name',
          label: 'Nome',
          tdClass: 'align-middle'
        },
        {
          key: 'actions',
          label: 'Ações',
          class: 'text-center',
          tdClass: 'align-middle',
          thStyle: {
            width: '100px'
          }
        }
      ],
      items: []
    };
  },
  mounted() {
    this.getItems();
  },
  methods: {
    getItems: function () {
      this.isBusy = true;
      httpGet('positions', json => this.items = json)
        .finally(() => this.isBusy = false);
    },
    doDelete: function (item) {
      httpDelete('positions/' + item.id, response => {
        if (response.ok) {
          this.getItems()
        } else {
          response.json().then(json => {
            showErrorResponseDetailsInToast(json, this.$bvToast);
          });
        }
      });
    }
  }
});
