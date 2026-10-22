# Vending Machine

- **Category:** OOP Foundations
- **Level:** Beginner
- **Priority:** P1
- **Target time:** 60 minutes
- **Language:** Java
- **Interview format:** Clarification → use cases → design → implementation → tests → trade-offs

## 1. Interview prompt

> Design and implement a vending machine in Java. The machine should allow customers to select products, insert money, purchase products, and receive change.

Treat this as a live machine-coding interview. Do not jump straight into class design or code. Drive the discussion step by step, state assumptions, and clarify requirements where needed.

## 2. Interview rules

- Start by thinking through the problem for a minute.
- Ask focused questions when behaviour is ambiguous.
- Summarize the agreed scope before designing.
- Explain your reasoning, but keep the design proportional to the requirements.
- Implement a working end-to-end flow before adding optional abstractions.
- Do not assume a design pattern is required just because this is an OOP exercise.

## 3. Interview stages and candidate tasks

### Stage 1 — Requirement clarification (0–8 minutes)

**Interviewer says:**

> Design and implement a vending machine. It should allow users to select products, insert money, and purchase products.

**Your task:** Ask clarification questions before designing.

Consider asking about:
- What products and inventory information does the machine maintain?
- Can a customer insert money before or after selecting a product?
- Which payment types are in scope?
- What should happen when the product is invalid or out of stock?
- What should happen when payment is insufficient?
- Can a customer cancel, and how should a refund work?
- How should change be calculated, and do we need to model available change denominations?
- Are persistence, hardware integration, and concurrent customers in scope?

Do not ask every possible question mechanically. Prioritize questions that affect the core design and propose sensible defaults for the rest.

**Candidate output:** A short list of clarified requirements and explicit assumptions.

**Interviewer checkpoint:** Can you explain what is in scope and what is intentionally excluded?

### Stage 2 — Identify use cases (8–12 minutes)

**Interviewer asks:**

> Walk me through the use cases you would support. Do not design classes yet. Tell me what a customer can do and how the machine should respond.

List the primary flow and failure/cancellation flows. For each use case, describe:
1. The action or input.
2. The condition that must be checked.
3. The expected result.
4. What state must or must not change on failure.

Use this table as a note-taking guide:

| Use case | Expected behaviour | Failure/edge case to consider |
|---|---|---|
| View/select a product | A valid product can be selected | Unknown product ID |
| Insert money | Payment amount is recorded | Invalid or unsupported amount |
| Purchase product | Product is dispensed and stock decreases once | Insufficient payment |
| Purchase unavailable product | No product is dispensed and no charge is retained | Product is out of stock |
| Return change | Excess payment is returned | Change cannot be provided, if that constraint is in scope |
| Cancel | The current payment is refunded | Cancel with no active transaction |
| Inspect inventory (if in scope) | Available products and quantities can be shown | Empty inventory |

This is a prompt, not a prescribed implementation. Confirm the final use cases with the interviewer.

### Stage 3 — Agree on scope (12–15 minutes)

For this exercise, use the following default scope unless your interviewer specifies otherwise:

**Included**
- Multiple products, each with an identifier, name, price, and stock quantity.
- Product selection.
- Money insertion and payment tracking for the current purchase.
- Purchase validation.
- Successful dispensing represented in application logic.
- Change calculation.
- Cancellation and refund.
- Clear handling of invalid product IDs, sold-out products, and insufficient payment.
- In-memory state and a simple Java implementation.

**Excluded for the first version**
- Database or file persistence.
- Physical vending hardware and payment-provider integration.
- Multiple customers using the machine concurrently.
- Complex coin inventory/change-making algorithms.
- Refunds involving external payment providers.

These exclusions keep the exercise achievable within 60 minutes. State any additional assumptions you make.

### Stage 4 — Propose the design (15–22 minutes)

**Interviewer asks:**

> How would you model this in Java? Walk me through the important objects and their responsibilities before writing code.

Your task:
- Identify the domain concepts and their responsibilities.
- Explain which object owns inventory and which object coordinates a purchase.
- Define the public operations the machine exposes.
- Decide how success and failure will be communicated.
- Describe how you will prevent invalid operations from corrupting state.
- Sketch relationships or a small UML diagram if it helps.

**Do not start with a large class hierarchy.** Keep the first design small enough to implement and test inside the time limit.

### Stage 5 — Implement the core flow (22–42 minutes)

Implement the smallest complete version in `Solution.java`.

Suggested order:
1. Represent products and their prices.
2. Maintain product stock.
3. Expose the machine operations.
4. Implement selection and payment tracking.
5. Implement purchase validation and successful purchase.
6. Handle change and cancellation/refund.
7. Make failure behaviour explicit.

Use integer minor currency units (for example, paise) or `BigDecimal` for money; do not use `double` for currency calculations.

Focus on the behaviour and invariants first. Add interfaces or design patterns only when they solve a real requirement.

### Stage 6 — Test and discuss trade-offs (42–55 minutes)

**Interviewer asks:**

> How would you prove that your implementation behaves correctly? Walk me through the tests you would run.

Cover at least:
- A successful purchase with exact payment.
- A successful purchase with excess payment and change.
- Insufficient payment.
- Selecting an unknown product.
- Attempting to buy a sold-out product.
- Cancelling and receiving a refund.
- Verifying stock changes exactly once after success.
- Verifying stock and payment state remain correct after a failed purchase.

Add automated tests when time allows. Explain any unimplemented edge cases honestly.

### Stage 7 — Review and extensions (55–60 minutes)

**Interviewer asks:**

> What would you improve if you had another 30 minutes? Which requirements would force you to change your design?

Potential discussion topics—not requirements for the initial solution:
- Cash and UPI payment methods.
- Multiple payment strategies.
- Persistent inventory.
- Concurrent purchase attempts.
- A limited coin/change inventory.
- A payment succeeding while physical dispensing fails.
- Maintaining an auditable transaction history.

Explain the trade-offs before proposing a refactor.

## 4. Business invariants to clarify and preserve

Use these to reason about correctness. They are behavioural expectations, not an implementation recipe.

- An invalid product selection must not accidentally charge the customer.
- An out-of-stock product must not be sold.
- Insufficient payment must not dispense the product.
- A failed purchase must not reduce inventory.
- A successful purchase must reduce stock exactly once.
- The customer must not lose inserted money when the purchase is cancelled.
- Change must be based on the accepted payment and product price.
- A transaction must not be completed twice by repeating the same operation.

Clarify the exact refund and change rules during requirement discovery, then make the agreed behaviour testable.

## 5. Interview evaluation rubric

After completing the exercise, assess yourself from 0–2 for each item:

| Area | 0 points | 1 point | 2 points |
|---|---|---|---|
| Clarification | Started coding without clarifying | Asked a few useful questions | Clarified critical behaviour and scope |
| Use cases | Missed major flows | Covered the happy path and some failures | Covered success, failures, cancellation, and refunds |
| Object design | Responsibilities are unclear | Mostly understandable design | Clear responsibilities and minimal coupling |
| Correctness | Core flow is broken | Happy path works, some gaps remain | Invariants hold across success and failure paths |
| Code quality | Hard to follow | Reasonably structured | Clear naming, small methods, explicit outcomes |
| Testing | No meaningful tests | Happy path tested | Important edge cases and state changes tested |
| Communication | Little explanation | Explains some choices | Explains assumptions and trade-offs clearly |
| Time management | No end-to-end result | Partial working flow | Working core flow plus meaningful tests |

**Maximum:** 16 points. This rubric is for practice, not a universal hiring-company scorecard.

## 6. Common interview mistakes

- Coding before understanding the expected behaviour.
- Asking vague questions such as “What use cases do you want?” without suggesting any.
- Overengineering with many interfaces, patterns, or layers before a basic purchase works.
- Using `double` for money.
- Updating stock or retaining payment before validating the purchase.
- Ignoring cancellation, refunds, sold-out products, and insufficient payment.
- Spending the entire interview drawing UML and leaving no time to implement.
- Claiming an edge case is handled without demonstrating it in code or tests.

## 7. Candidate notes

Use this section during practice.

### Requirement questions
- 
- 
- 

### Agreed assumptions and exclusions
- 
- 

### Use cases
- 
- 
- 

### Proposed design and trade-offs
- 
- 

### Tests still to add
- 
- 

## 8. Implementation checklist

- [ ] Clarify requirements and assumptions.
- [ ] Identify primary and failure use cases.
- [ ] Agree on scope and exclusions.
- [ ] Define entities, responsibilities, operations, and relationships.
- [ ] Implement the successful purchase flow.
- [ ] Handle insufficient payment, invalid products, and sold-out products.
- [ ] Implement cancellation, refunds, and change according to the agreed rules.
- [ ] Verify inventory and payment state after both success and failure.
- [ ] Add tests for happy paths and edge cases.
- [ ] Explain design choices and trade-offs.
- [ ] Review against the interview evaluation rubric.

Implement the problem in `Solution.java`. This repository intentionally contains placeholders only.
