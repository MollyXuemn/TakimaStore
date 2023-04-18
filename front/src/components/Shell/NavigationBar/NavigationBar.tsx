import styles from "./NavigationBar.module.scss";
import { NavLink } from "react-router-dom";
import { UserContext } from "../../../App";
import React, { useContext } from "react";
import {
  ShoppingCartOutlined,
  UsergroupDeleteOutlined,
  AppstoreOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { useSelector } from "react-redux";
import { RootState } from "../../../stores/redux";
import { Badge, Menu, Space } from "@mantine/core";
import Divider = Menu.Divider;

const linkStyle = ({ isActive }: { isActive: boolean }) => ({
  backgroundColor: isActive ? "#e2E2E210" : "",
  borderRadius: "10px",
  padding: "5px",
});
function NavShoppingCart() {
  const cartState = useSelector((state: RootState) => state.cart);
  return (
    <NavLink to="/cart" style={linkStyle}>
      <ShoppingCartOutlined />

      <span>Cart</span>
      <Space w="md" />
      <Badge color="gray" size="xl">
        {cartState.articles.length}
      </Badge>
    </NavLink>
  );
}

export default function NavigationBar() {
  const user = useContext(UserContext);

  return (
    <div className={styles.navigationBar}>
      <>
        <NavLink to="/" style={linkStyle}>
          <img alt="" src={"/takima_logo.png"} width="35" />
        </NavLink>
        <NavLink to="/articles" style={linkStyle}>
          <AppstoreOutlined />
          <span className={styles.linkElement}>Articles</span>
        </NavLink>
        <NavLink to="/sellers" style={linkStyle}>
          <UsergroupDeleteOutlined />
          <span className={styles.linkElement}>Sellers</span>
        </NavLink>
      </>
      <>
        <NavShoppingCart />
        <NavLink to="/profile" style={linkStyle}>
          <UserOutlined />
          {user && (
            <>
              {user.firstName} {user.lastName}
            </>
          )}
        </NavLink>
      </>
    </div>
  );
}
