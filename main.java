class Wallet {
  public int bill1 = 0;
  public int bill5 = 0;
  public int bill10 = 0;
  public int bill20 = 0;
  public int bill50 = 0;
  public int bill100 = 0;

  // コンストラクタ
  public Wallet() {
  }

  // コンストラクタ（引数あり）
  public Wallet(int bill1, int bill5, int bill10, int bill20, int bill50, int bill100) {
    this.bill1 = bill1;
    this.bill5 = bill5;
    this.bill10 = bill10;
    this.bill20 = bill20;
    this.bill50 = bill50;
    this.bill100 = bill100;
  }

  // 合計所持金額を返す
  public int getTotalMoney() {
    return (1 * bill1) + (5 * bill5) + (10 * bill10) + (20 * bill20) + (50 * bill50) + (100 * bill100);
  }

  // 指定されたbillとその枚数を、財布に追加する。
  public int insertBill(int bill, int amount) {
    switch (bill) {
      case (1):
        this.bill1 += amount;
        break;
      case (5):
        this.bill5 += amount;
        break;
      case (10):
        this.bill10 += amount;
        break;
      case (20):
        this.bill20 += amount;
        break;
      case (50):
        this.bill50 += amount;
        break;
      case (100):
        this.bill100 += amount;
        break;
      default:
        return 0;
    }

    return bill * amount;
  }

  // 指定されたbillとその枚数を、財布から削除する。
  public int removeBill(int bill, int amount) {
    switch (bill) {
      case (1):
        this.bill1 -= amount;
        break;
      case (5):
        this.bill5 -= amount;
        break;
      case (10):
        this.bill10 -= amount;
        break;
      case (20):
        this.bill20 -= amount;
        break;
      case (50):
        this.bill50 -= amount;
        break;
      case (100):
        this.bill100 -= amount;
        break;
      default:
        return 0;
    }

    return bill * amount;
  }
}

class Person {
  public String firstName;
  public String lastName;
  public int age;
  public double heightM;
  public double weightKg;
  private Wallet wallet = null;
  public String denomination = "highestFirst"; // お金の取り扱い方

  // コンストラクタ
  public Person(String firstName, String lastName, int age, double heightM, double weightKg) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.heightM = heightM;
    this.weightKg = weightKg;
  }

  // コンストラクタ（デフォルト金額あり）
  public Person(String firstName, String lastName, int age, double heightM, double weightKg, int initialMoney) {
    this(firstName, lastName, age, heightM, weightKg);

    this.getPayed(initialMoney);
  }

  // フルネームを返す
  public String getFullName() {
    return this.firstName + " " + this.lastName;
  }

  // 合計所持金額を返す
  public int getCash() {
    if (this.wallet == null) {
      return 0;
    }
    return this.wallet.getTotalMoney();
  }

  public int[] exchange(int money) {
    if (this.denomination == "highestFirst") {
      int bill100 = money / 100;
      money %= 100;

      int bill50 = money / 50;
      money %= 50;

      int bill20 = money / 20;
      money %= 20;

      int bill10 = money / 10;
      money %= 10;

      int bill5 = money / 5;
      money %= 5;

      int bill1 = money;

      return new int[] { bill100, bill50, bill20, bill10, bill5, bill1 };

    } else if (this.denomination == "dollars") {
      return new int[] { 0, 0, 0, 0, 0, money };

    } else if (this.denomination == "twenties") {
      int bill20 = money / 20;
      money %= 20;

      int bill100 = money / 100;
      money %= 100;

      int bill50 = money / 50;
      money %= 50;

      int bill10 = money / 10;
      money %= 10;

      int bill5 = money / 5;
      money %= 5;

      int bill1 = money;

      return new int[] { bill100, bill50, bill20, bill10, bill5, bill1 };
    } else {
      return new int[] {};
    }
  }

  // 設定されたお金の取り扱い方によって、財布にお金を追加する
  public int[] getPayed(int money) {
    if (this.wallet == null) {
      return new int[]{};
    }

    int[] moneyArr = this.exchange(money);
    int[] moneyType = {100, 50, 20, 10, 5, 1};

    for (int i = 0; i < moneyType.length; i++) {
      this.wallet.insertBill(moneyType[i], moneyArr[i]);
    }
    return moneyArr;
  }

  // 設定されたお金の取り扱い方によって、財布からお金を抜く
  public int[] spendMoney(int money) {
    if (this.wallet == null) {
      return new int[]{};
    }

    int[] moneyArr = this.exchange(money);
    int[] moneyType = {100, 50, 20, 10, 5, 1};

    for (int i = 0; i < moneyType.length; i++) {
      this.wallet.removeBill(moneyType[i], moneyArr[i]);
    }
    return moneyArr;
  }

  // 財布を追加
  public void addWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  // 財布を削除
  public Wallet dropWallet() {
    Wallet buffer = this.wallet;
    this.wallet = null;
    return buffer;
  }

  // お金の取り扱い方を設定する
  public void setDenominationPreference(String denomination) {
    if (denomination.equals("highestFirst") || denomination.equals("dollars") || denomination.equals("twenties")) {
      this.denomination = denomination;
    } else {
      return;
    }
  }

  public void printState() {
    System.out.println("firstname - " + this.firstName);
    System.out.println("lastname - " + this.lastName);
    System.out.println("age - " + this.age);
    System.out.println("height - " + this.heightM);
    System.out.println("weight - " + this.weightKg);
    System.out.println("Current Money - " + this.getCash());
    System.out.println();
  }
}

class Main {
  public static void main(String[] args) {
    Person p = new Person("Ryu", "Poolhopper", 40, 1.8, 140);
    p.setDenominationPreference("dollars");

    // 財布を追加する
    p.addWallet(new Wallet());

    // getPaidメソッドでお金を追加する
    p.getPayed(123);

    System.out.println(p.getCash());

    // spendMoneyメソッドでお金を使う
    p.spendMoney(10);

    // getCashメソッドで所持金額を表示する
    System.out.println(p.getCash());

    // 財布を削除する
    p.dropWallet();

    // getCashメソッドをする。→ 0が返ってくるはず。
    System.out.println(p.getCash());
  }
}

// 学んだこと
// 文字列の＝＝は参照の一致しているかどうかの確認になる
// 