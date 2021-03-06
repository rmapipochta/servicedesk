export default () => {
  return {
    restrict: 'E',
    templateUrl: '/public/application/module/common/dictionary/ui/ticket-comment/template/comment.html',
    scope: {
      ticket: '='
    },
    controller: ($scope, ticketCommentService, $timeout, $interval, $attrs, converter, logged, Monitor) => {

      var monitor = Monitor.instance();

      $scope.covered = false;
      $scope.logged = logged.logged;

      var resetComment = () => {
        $scope.comment = {
          ticket: converter.out($scope.ticket)
        };
      };

      var updateCommentsStore = silent => {
        if (!$scope.ticket.id) {
          return;
        }

        if (!silent) {
          $scope.covered = true;
        }

        return ticketCommentService.list({ticketId: $scope.ticket.id}).then(response => {
          $scope.commentStore = response;
          if (!silent) {
            resetComment();
            $scope.covered = false;
          }
          return response;
        });
      };

      $scope.openEdit = comment => {
        if (comment.author.id !== $scope.logged.account.id) {
          return;
        }
        $scope.covered = true;
        monitor.stop().then(() => {
          $scope.editComment = comment;
          $scope.covered = false;
        });
      };

      // save edited comment
      $scope.closeEdit = (comment) => {

        $scope.covered = true;
        ticketCommentService.update(comment).then(() => {
          $scope.covered = false;
          $scope.editComment = null;
          monitor.start();
        });
      };

      $scope.focus = () => $scope.rows = 3;
      $scope.blur = () => $scope.rows = 1;

      $scope.send = comment => {
        $scope.covered = true;
        return ticketCommentService.create(comment).then(() => {
          updateCommentsStore();
          $scope.covered = false;
        });
      };

      resetComment();
      $scope.blur();
      monitor.setService(() => updateCommentsStore(true));

      if ($scope.ticket.id) {
        monitor.start();
        $scope.$on("$destroy", () => monitor.stop());
      }
    }
  }
}
