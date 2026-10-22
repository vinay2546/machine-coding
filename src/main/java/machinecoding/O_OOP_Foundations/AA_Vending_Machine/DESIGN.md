# Vending Machine — Design and API Contract

## Scope

A single-threaded, in-memory vending machine. It supports multiple products, product selection, inserting money in paise, purchasing, change, cancellation/refunds, and inventory inspection. Physical dispensing is represented by a successful result; no hardware, database, external payment provider, coin inventory, or concurrency is modelled.

## Public API

| Operation | Contract |
|---|---|
| `getProducts()` | Returns a snapshot of product/stock entries. |
| `getProduct(productId)` | Returns the product, or empty when the ID is unknown. |
| `selectProduct(productId)` | Selects a known, in-stock product; a failed selection does not change the current selection or balance. |
| `insertMoney(amountInPaise)` | Adds a positive amount to the current balance. Zero/negative amounts are rejected. |
| `getBalance()` | Returns the current transaction balance in paise. |
| `purchase()` | Returns a typed result. A failed purchase preserves stock and balance; success decreases stock once, returns change, and clears the transaction. |
| `cancelTransaction()` | Returns the inserted balance as a refund and clears the transaction. |
| `getInventory()` | Returns product and quantity snapshots. |

## Decisions and assumptions

- Money is represented as integer paise. ₹10.00 is `1000`; floating-point types are not used for currency.
- Money may be inserted before or after product selection. Multiple insertions accumulate.
- The customer may switch to another available product before purchase; the current balance is retained.
- A failed purchase does not silently discard money. The customer can add money, select another product, or cancel.
- This version assumes change can always be returned. Modelling finite coin/change inventory is an extension.
- Business failures are represented with enums and result objects rather than exceptions. Invalid programmer input (for example, a non-positive inserted amount) is rejected with `IllegalArgumentException`.
- Inventory views are snapshots, not mutable internal collections.

## Invariants

1. Product prices are positive and product IDs/names are nonblank.
2. Stock quantities never become negative.
3. A failed purchase does not change stock or balance.
4. A successful purchase reduces stock exactly once.
5. Change equals inserted balance minus product price.
6. Cancellation refunds the entire inserted balance.
7. Completing or cancelling a transaction resets the selected product and balance.

## Suggested implementation sequence

1. Model products and typed operation results.
2. Encapsulate product stock in `Inventory`.
3. Implement the orchestration in `VendingMachine`.
4. Demonstrate the API in `Solution.main`.
5. Verify successful and failed flows with unit tests.

## Running tests

From the repository root, run `mvn test`.

The package follows the existing repository convention: `machinecoding.O_OOP_Foundations.AA_Vending_Machine`.
