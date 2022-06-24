angular.module('market-front').controller('cartShowController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    let currentPageIndex = 1;

    $scope.loadCart = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + '/cart',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    }

    $scope.removeInfo = function (product) {
            $http({
                url: contextPath + '/cart/'+ product.id,
                method: 'DELETE'
            }).then(function (response) {
                console.log(response);
                $scope.loadCart(currentPageIndex);
            });
        }

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.nextPage = function () {
        currentPageIndex++;
        if (currentPageIndex > $scope.productsPage.totalPages) {
            currentPageIndex = $scope.productsPage.totalPages;
        }
        $scope.loadCart(currentPageIndex);
    }

    $scope.prevPage = function () {
        currentPageIndex--;
        if (currentPageIndex < 1) {
            currentPageIndex = 1;
        }
        $scope.loadCart(currentPageIndex);
    }

    $scope.loadCart(1);

});