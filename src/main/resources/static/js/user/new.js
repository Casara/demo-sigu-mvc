Vue.use(VueMask.VueMaskPlugin);
Vue.component('ValidationObserver', VeeValidate.ValidationObserver);
Vue.component('ValidationProvider', VeeValidate.ValidationProvider);

new Vue({
  el: '#main',
  data() {
    return {
      isBusy: false,
      form: {
        name: null,
        cpf: null,
        birthDate: null,
        gender: null,
        positionId: null,
        profiles: []
      },
      positions: [],
      profiles: [],
      selectedProfiles: []
    };
  },
  mounted() {
    this.getPositions();
    this.getProfiles();
  },
  computed: {
    availableProfiles() {
      return this.profiles
        .filter(profile => this.selectedProfiles.indexOf(profile.name) === -1)
        .map(profile => profile.name);
    },
  },
  methods: {
    getPositions: function () {
      httpGet('positions', json => this.positions = json);
    },
    getProfiles: function () {
      httpGet('profiles', json => this.profiles = json);
    },
    getValidationState({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null;
    },
    resetForm() {
      this.form = {
        name: null,
        cpf: null,
        birthDate: null,
        gender: null,
        positionId: null,
        profiles: []
      };
    },
    onSubmit(evt) {
      this.isBusy = true;
      this.form.profiles = this.profiles
        .filter(profile => this.selectedProfiles.indexOf(profile.name) !== -1)
        .map(profile => profile.id);
      httpSubmit('users', null, this.form, this.$bvToast, this.$refs.observer)
        .finally(() => this.isBusy = false);
    }
  }
});
