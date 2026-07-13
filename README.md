# PRG381 Group 4 - University Cleaning Inventory & Issuance System

Belgium Campus PRG381 group project, **Track B**: a University Cleaning
Inventory & Issuance System built as a **Java Swing desktop application**.

Storekeepers and supervisors use it to manage cleaning materials, suppliers and
cleaners, and to issue stock. Issuing deducts stock in a single transaction,
blocks issues that exceed available quantity, and flags low-stock items.

## Stack

| Part      | Choice                                             |
|-----------|----------------------------------------------------|
| Language  | Java 17                                             |
| UI        | Java Swing (hand-coded CRUD windows, separate JFrames) |
| Database  | Apache Derby 10.16.1.1, **embedded**               |
| Build/IDE | NetBeans with Ant (not Maven)                      |
| Structure | Strict MVC (model / view / controller, plus util)  |

## Architecture

```
prg381group4/
├── PRG381Group4.java   Main: boots Derby, seeds the DB, opens the login window
├── model/              Entities + persistence
│   ├── Person (abstract) -> User, Cleaner
│   ├── Supplier, Material, Issuance
│   └── dao/            DBConnection, SchemaInitializer, Dao/BaseDao + concrete DAOs
├── controller/         Business rules; the only thing the view may call
├── view/               Swing windows
└── util/               Validator, PasswordUtil, Session, custom exceptions
```

Golden rule: **`view` must never import from `model.dao`.** The view talks to
controllers only.

## How to run

You need **JDK 17** and **NetBeans**.

1. **Get the Derby jars.** Download `db-derby-10.16.1.1-bin.zip` from
   https://db.apache.org/derby/derby_downloads.html and copy `derby.jar`,
   `derbyshared.jar` and `derbytools.jar` into `PRG381Group4/lib/`.
   See `PRG381Group4/lib/README.txt` for details. (Do not use Derby 10.17 -
   it requires Java 21.)
2. **Add them to the classpath.** In NetBeans: right-click the project ->
   Properties -> Libraries -> Classpath -> Add JAR/Folder -> select the three
   jars. Choose the **Relative Path** option, or the project breaks on other
   machines.
3. **Run** `PRG381Group4.java`. On first run the embedded database is created
   and seeded, and a `cleaninvdb/` folder appears in the project - that folder
   IS the database and is git-ignored on purpose.

## Demo logins

| Username | Password   | Role        |
|----------|------------|-------------|
| admin    | Admin@123  | Supervisor  |
| store    | Store@123  | Storekeeper |

Supervisors can delete and manage users; storekeepers cannot.

## Team & file ownership

Do not edit a file another member owns.

| Person   | Owns                                                                                  |
|----------|---------------------------------------------------------------------------------------|
| Robert   | `model/*`, `DBConnection`, `SchemaInitializer`, `PRG381Group4.java`, project config    |
| Juan     | `CleanerDao`, `IssuanceDao.delete()`, `CleanerController`, `IssuanceController`, `ReportController` |
| Tammy    | `UserDao`, `AuthController`, password policy in `Validator`, role guards               |
| Pandora  | `view/*` (all Swing windows)                                                          |

## Notes

- Never commit `cleaninvdb/`, `build/`, `dist/`, `nbproject/private/` or `derby.log`.
- The Derby jars in `PRG381Group4/lib/` are committed on purpose.
