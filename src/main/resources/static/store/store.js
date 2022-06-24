angular.module('market-front').controller('storeController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/market/api/v1';

    let currentPageIndex = 1;

    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + '/products',
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


    $scope.showInfo = function (product) {
        alert(product.title);
    }

    $scope.addToCart = function (product) {
             $http.post(contextPath + '/cart', product)
            .then(function successCallback(response) {
                    alert("Продукт успешно добавлен");
                    $location.path('/store');
                }, function failCallback(response) {
                    alert(response.data.messages);
                }
            );
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
        $scope.loadProducts(currentPageIndex);
    }

    $scope.prevPage = function () {
        currentPageIndex--;
        if (currentPageIndex < 1) {
            currentPageIndex = 1;
        }
        $scope.loadProducts(currentPageIndex);
    }

    $scope.navToEditProductPage = function (productId){
        $location.path('/edit_product/'+ productId);
    }


    $scope.loadProducts(1);
});