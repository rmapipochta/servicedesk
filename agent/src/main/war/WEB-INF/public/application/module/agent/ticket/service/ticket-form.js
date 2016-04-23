angular.module("backend.ticket")
  .factory("ticketForm", function ($uibModal) {
    return {
      open: function (ticket) {
        $uibModal.open({
          controller: 'TicketFormController',
          resolve: {
            record: ticket
          },
          size: 'lg',
          backdrop: 'static',
          templateUrl: '/public/application/template/agent/ticket/form/ticket.html'
        });
      }
    };
  });