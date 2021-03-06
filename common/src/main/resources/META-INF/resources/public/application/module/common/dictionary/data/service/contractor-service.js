export default function ($http) {
  return {
    list: function (start, limit) {
      return $http.get("/contractor", {}).then(function (response) {
        return response.data;
      });
    },

    update: function (record) {
      return $http.put("/contractor", record).then(function (response) {
        return response.data;
      });
    },

    new: function (record) {
      return $http.post("/contractor", record).then(function (response) {
        return response.data;
      });
    }
  }
}