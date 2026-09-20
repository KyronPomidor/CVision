import styles from "./Perk.module.css";
import CoffeeIcon from "./assets/coffee.svg";
import LaptopIcon from "./assets/laptop.svg";
import UsersIcon from "./assets/users.svg";
import TrendingIcon from "./assets/trending.svg";

const PERK_TYPES = {
    coffee:   { icon: CoffeeIcon,       label: "Free Coffee" },
    growth:   { icon: TrendingIcon,     label: "Career Growth" },
    flexible: { icon: LaptopIcon,       label: "Flexible Work" },
    team:     { icon: UsersIcon,        label: "Supportive Team" },
  };

function Perk({ type }) {
    const perk = PERK_TYPES[type];
    if (!perk) return null;

    return (
        <div className={styles.perk}>
            <span className={styles.iconCircle}>
                <img src={perk.icon} className={styles.perkLogo}/>
            </span>
            <span className={styles.perkDescription}>
                {perk.label}
            </span>
        </div>
    )
}

export default Perk