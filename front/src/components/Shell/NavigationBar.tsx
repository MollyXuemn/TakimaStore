import styles from "./NavigationBar.module.scss";
import { NavLink } from "react-router-dom";

const linkStyle = ({ isActive }: { isActive: boolean }) => ({
  backgroundColor: isActive ? "#e2E2E210" : "",
  borderRadius: "10px",
  padding: "5px",
});

export default function NavigationBar() {
  return (
    <div className={styles.navigationBar}>
      <div>
        <NavLink to="/" style={linkStyle}>
          <img alt="" src={"/takima_logo.png"} width="35" />
        </NavLink>
        <NavLink to="/articles" style={linkStyle}>
          <div className={styles.linkElement}>
            <span>Articles</span>
          </div>
        </NavLink>
        <NavLink to="/sellers" style={linkStyle}>
          <div className={styles.linkElement}>
            <span>Sellers</span>
          </div>
        </NavLink>
      </div>

      <div>
        <NavLink to="/card" style={linkStyle}>
          <div>
            <div>Card</div>
          </div>
        </NavLink>
        <NavLink to="/profile" style={linkStyle}>
          <div>
            <div>Profile</div>
          </div>
        </NavLink>
      </div>
    </div>
  );
}
