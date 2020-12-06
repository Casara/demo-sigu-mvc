Vue.use(VueMask.VueMaskPlugin);

Vue.filter('genderName', function (value) {
  if (!isBlank(value) && ['F', 'M'].indexOf(value) !== -1) {
    return value === 'F' ? 'Feminino' : 'Masculino';
  }
});

Vue.filter('fmtBirthDate', function (value) {
  if (!isBlank(value)) {
    return value.replace(/(\d{4})-(\d{2})-(\d{2})/, '$3/$2/$1');
  }
});

Vue.filter('positionName', function (value) {
  if (!isBlank(value)) {
    return value.name;
  }
});

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
          key: 'cpf',
          label: 'CPF',
          tdClass: 'align-middle'
        },
        {
          key: 'birthDate',
          label: 'Data de nascimento',
          tdClass: 'align-middle'
        },
        {
          key: 'gender',
          label: 'Sexo',
          tdClass: 'align-middle'
        },
        {
          key: 'position',
          label: 'Cargo',
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
      httpGet('users', json => this.items = json)
        .finally(() => this.isBusy = false);
    },
    doDelete: function (item) {
      httpDelete('users/' + item.id, () => this.getItems());
    }
  }
});
