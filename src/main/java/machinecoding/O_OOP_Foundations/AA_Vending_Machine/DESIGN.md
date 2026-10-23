# Vending Machine — Design and API Contract

## Scope

A single-threaded, in-memory vending machine. It supports multiple products, product selection, inserting money in paise, purchasing, change, cancellation/refunds, and inventory inspection. Physical dispensing is represented by a successful result; no hardware, database, external payment provider, coin inventory, or concurrency is modelled.

## Public API

| Operation | Contract |
|---|---|
| `getProducts()` | Returns an immutable snapshot of product definitions; stock quantities are available through `getInventory()`. |
| `getProduct(productId)` | Returns the product, or `Optional.empty()` when the ID is unknown. |
| `selectProduct(productId)` | Selects a known, in-stock product. Returns `SELECTED`, `PRODUCT_NOT_FOUND`, or `OUT_OF_STOCK`. A failed selection leaves the current selection and balance unchanged. |
| `insertMoney(amountInPaise)` | Adds a positive amount to the current balance. Zero/negative amounts are rejected with `IllegalArgumentException`; integer overflow is rejected by `Math.addExact`. |
| `getBalance()` | Returns the current transaction balance in paise. |
| `purchase()` | Returns a typed `PurchaseResult`. A failed purchase preserves stock and balance; success decreases stock once, returns change, and clears the transaction. |
| `cancelTransaction()` | Returns a `TransactionResult` containing the refund amount and whether a transaction was cancelled; then clears the selection and balance. |
| `getInventory()` | Returns immutable inventory-item snapshots containing product details and stock quantities. |

## Object responsibilities

- **`Product`** — immutable product identity, name, and positive price.
- **`Inventory`** — owns product definitions and quantities; validates additions and prevents stock from becoming negative.
- **`VendingMachine`** — coordinates the current customer's selected product, inserted balance, purchase validation, cancellation, and transaction reset.
- **`SelectionResult`, `PurchaseStatus`, `PurchaseResult`, `TransactionResult`, `InventoryItem`** — make outcomes and snapshots explicit rather than exposing mutable internal collections.

The first version intentionally keeps the selected product and payment balance inside `VendingMachine`. A separate `PaymentSession` or payment strategy can be introduced when multiple concurrent sessions or payment methods become actual requirements; adding them now would make a 60-minute beginner exercise harder without improving the required behaviour.

## Decisions and assumptions

- Money is represented as integer paise. ₹10.00 is `1000`; floating-point types are not used for currency.
- Money may be inserted before or after product selection. Multiple insertions accumulate.
- The customer may switch to another available product before purchase; the current balance is retained.
- A failed purchase does not silently discard money. The customer can add money, select another product, or cancel.
- This version assumes change can always be returned. Modelling finite coin/change inventory is an extension.
- Business failures are represented with enums and result objects rather than exceptions. Invalid programmer input (for example, a non-positive inserted amount) is rejected with `IllegalArgumentException`.
- Inventory views are snapshots, not mutable internal collections.
- The machine is single-threaded; synchronization and simultaneous purchase handling are out of scope.

## Invariants

1. Product prices are positive and product IDs/names are nonblank.
2. Stock quantities never become negative.
3. A failed purchase does not change stock or balance.
4. A successful purchase reduces stock exactly once.
5. Change equals inserted balance minus product price.
6. Cancellation refunds the entire inserted balance.
7. Completing or cancelling a transaction resets the selected product and balance.
8. An invalid selection does not overwrite a previously valid selection.
9. A second purchase call after a successful purchase cannot dispense the same product again without a new selection and payment.

## Suggested implementation sequence

1. Model products and typed operation results.
2. Encapsulate product stock in `Inventory`.
3. Implement the orchestration in `VendingMachine`.
4. Demonstrate the API in `Solution.main`.
5. Verify successful and failed flows with unit tests.

## Running tests

From the repository root, run `mvn test`.

The package follows the existing repository convention: `machinecoding.O_OOP_Foundations.AA_Vending_Machine`.
