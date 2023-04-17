import styles from "./NavigationBar.module.scss";
import { NavLink } from "react-router-dom";
import { UserContext } from "../../../App";
import { useContext } from "react";
import {
  ShoppingCartOutlined,
  UsergroupDeleteOutlined,
  AppstoreOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { Space } from "antd";

const linkStyle = ({ isActive }: { isActive: boolean }) => ({
  backgroundColor: isActive ? "#e2E2E210" : "",
  borderRadius: "10px",
  padding: "5px",
});

export default function NavigationBar() {
  const user = useContext(UserContext);

  return (
    <div className={styles.navigationBar}>
      <div>
        <NavLink to="/" style={linkStyle}>
          <img alt="" src={"/takima_logo.png"} width="35" />
        </NavLink>
        <NavLink to="/articles" style={linkStyle}>
          <AppstoreOutlined />
          <div className={styles.linkElement}>
            <span>Articles</span>
          </div>
        </NavLink>
        <NavLink to="/sellers" style={linkStyle}>
          <UsergroupDeleteOutlined />
          <div className={styles.linkElement}>
            <span>Sellers</span>
          </div>
        </NavLink>
      </div>

      <div>
        <NavLink to="/card" style={linkStyle}>
          <div>
            <ShoppingCartOutlined />
          </div>
        </NavLink>
        <NavLink to="/profile" style={linkStyle}>
          <UserOutlined />
          {user && (
            <div>
              {user.firstName} {user.lastName}
            </div>
          )}
        </NavLink>
      </div>
    </div>
  );
}
