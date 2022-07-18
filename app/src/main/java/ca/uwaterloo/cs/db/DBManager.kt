package ca.uwaterloo.cs.db

import ca.uwaterloo.cs.Listener
import ca.uwaterloo.cs.bemodels.SignUpFarmer
import ca.uwaterloo.cs.bemodels.SignUpWorker
import ca.uwaterloo.cs.bemodels.UserProfileFarmer
import ca.uwaterloo.cs.harvest.HarvestInformation
import ca.uwaterloo.cs.product.ProductInformation

class DBManager {
    private val idResolver = IdResolver()
    private val dbStoreDFCManager = DBStoreDFC()
    private val dbStoreInternal = DBStoreInternal()
    private val dbGetInternal = DBGetInternal()

    // returns true if there is no user with the username false otherwise
    fun storeSignUpFarmer(signUpFarmer: SignUpFarmer){
        dbStoreInternal.storeSignUpFarmer(signUpFarmer)
    }

    // returns true if the userName exists and it actually has that password
    fun authenticate(userName: String, password: String, listener: Listener<Boolean>){
        dbGetInternal.authenticate(userName, password, listener)
    }

    // returns true if there is no user with the username
    // neither enterprise with the enterpriseName and the enterpriseId is true
    fun storeSignUpWorker(signUpWorker: SignUpWorker){
        dbStoreInternal.storeSignUpWorker(signUpWorker)
    }

    // if the product is being created for the first time add productIdString to be null
    fun storeProductInformation(userId: String, productInformation: ProductInformation){
        val productIdString = productInformation.productId
        val productCreation = productIdString == null

        // DFC storage
        val DFCSuppliedProductId = idResolver.standardResolver(productIdString, IdType.DFCSuppliedProductId)
        dbStoreDFCManager.storeProductInformation(productCreation, DFCSuppliedProductId, productInformation)

        // Internal storage
        val productId = Id(DFCSuppliedProductId.idValue, IdType.ProductId)
        dbStoreInternal.storeProductInformation(productCreation, userId, productId, productInformation)
    }

    fun getProductInformation(userId: String, belistener: Listener<List<ProductInformation>>){
        dbGetInternal.getProductInformation(userId, belistener)
    }

    // if the harvest is being created for the first time add harvestId to be null in the HarvestInformation
    fun storeHarvestInformation(userId: String, harvestInformation: HarvestInformation){
        val harvestIdString = harvestInformation.harvestId
        val harvestCreation = harvestIdString == null

        val harvestId = idResolver.standardResolver(harvestIdString, IdType.HarvestId)
        dbStoreInternal.storeHarvestInformation(
            harvestCreation,
            userId,
            harvestId,
            harvestInformation
        )
    }

    fun getHarvestInformation(workerUserId: String, belistener: Listener<List<HarvestInformation>>){
        dbGetInternal.getHarvestInformation(workerUserId, belistener)
    }

    fun getAllHarvestsFromFarmer(farmerUserId: String, belistener: Listener<List<HarvestInformation>>){

    }

    fun storeUserProfile(userProfileFarmer: UserProfileFarmer){
    }

    fun getUserProfile(userName: String, listener: Listener<UserProfileFarmer>){
    }

}

class DBManagerTest() {
    private val userId1 = "messinotcom"
    private val dbManager = DBManager()

    private fun testSignUp() {
        val signUpFarmer = SignUpFarmer(userId1,
            "messi2",
            "messi2",
            "messi3",
            "messi land")

        dbManager.storeSignUpFarmer(signUpFarmer)
    }

    private fun simple1StoreProductTest() {
        val productInformation =
            ProductInformation(
                null,
                "new name",
                "vamos fugis",
                13,
                17L,
                "",
                platform1 = true,
                platform2 = false
            )
        dbManager.storeProductInformation(userId1, productInformation)
    }

    private fun simple2StoreProductTest() {
        val productInformation =
            ProductInformation(
                null,
                "not new name",
                "nao vamos fugir",
                13,
                17L,
                "",
                platform1 = true,
                platform2 = false
            )
        dbManager.storeProductInformation(userId1, productInformation)
    }

    private fun simpleGetProductTest(){
        class ListenerImpl() : Listener<List<ProductInformation>>() {
            override fun activate(input: List<ProductInformation>) {
            }
        }
        val listener = ListenerImpl()
        dbManager.getProductInformation(userId1, listener)
    }

    fun part1ProductTest(){
        testSignUp()
        Thread.sleep(4_000)  // wait for 1 second
        simple1StoreProductTest()
    }

    fun part2ProductTest(){
        simple2StoreProductTest()
        Thread.sleep(4_000)  // wait for 1 second
        simpleGetProductTest()
    }
}
