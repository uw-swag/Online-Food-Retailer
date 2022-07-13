package ca.uwaterloo.cs.db

import ca.uwaterloo.cs.product.ProductInformation
import ca.uwaterloo.cs.models.CatalogItem
import ca.uwaterloo.cs.models.Offer
import ca.uwaterloo.cs.models.Quantity
import ca.uwaterloo.cs.models.SuppliedProduct

class ProductInformationContentIngestion {
    fun getSuppliedProduct(productId: String, productInformation: ProductInformation, catalogItemId: List<String>): SuppliedProduct{
        return SuppliedProduct(
            productId,
            productInformation.description,
            getQuantity(productInformation),
            referencedBy = catalogItemId,
            platform1 = productInformation.platform1,
            platform2 = productInformation.platform2
        )
    }

    fun getOffer(offerId: String, productInformation: ProductInformation): Offer {
        return Offer(
            offerId,
            productInformation.price.toString()
        )
    }

    fun getCatalogItem(catalogItemId: String, productInformation: ProductInformation, offerId: Id): CatalogItem{
        return CatalogItem(
            catalogItemId,
            productInformation.amount.toString(),
            listOf(offerId.idValue)
        )
    }

    private fun getQuantity(productInformation: ProductInformation): Quantity{
        return Quantity(
            "u",
            productInformation.amount.toString()
        )
    }
}