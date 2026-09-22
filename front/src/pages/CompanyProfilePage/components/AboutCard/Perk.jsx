import styles from "./Perk.module.css";
import typography from "../../../../Typography.module.css";
import CoffeeIcon from "./assets/coffee.svg?react";
import LaptopIcon from "./assets/laptop.svg?react";
import UsersIcon from "./assets/users.svg?react";
import TrendingIcon from "./assets/trending.svg?react";

const PERK_TYPES = {
  coffee: { icon: CoffeeIcon, label: "Free Coffee" },
  growth: { icon: TrendingIcon, label: "Career Growth" },
  flexible: { icon: LaptopIcon, label: "Flexible Work" },
  team: { icon: UsersIcon, label: "Supportive Team" },
};

function Perk({ type }) {
  const perk = PERK_TYPES[type];
  if (!perk) return null;
  const Icon = perk.icon;

  return (
    <div className={styles.perk}>
      <span className={styles.iconCircle}>
        <Icon className={styles.perkLogo} aria-hidden="true" />
      </span>
      <span className={`${typography.mainTextImportant} ${styles.perkDescription}`}>{perk.label}</span>
    </div>
  );
}

export default Perk;
