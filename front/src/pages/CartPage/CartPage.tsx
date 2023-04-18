import { useDispatch, useSelector } from "react-redux";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../stores/redux";
import { removeFromCart } from "../store/cartSlice";
import { Article } from "../../components/article/article";
export default function CartPage() {
  const articles = useSelector((state: RootState) => state.cart.articles);
    const dispatch = useDispatch();

    const handleRemoveFromCart = (article: Article) => {
      dispatch(removeFromCart(article));
    };

    return (
      <div>
        {cartItems.map((cartItem) => (
          <div key={cartItem.article.id}>
            <h3>{cartItem.article.title}</h3>
            <p>Price: {cartItem.article.price}</p>
            <p>Quantity: {cartItem.quantity}</p>
            <button onClick={() => handleRemoveFromCart(cartItem.article)}>
              Remove
            </button>
          </div>
        ))}
      </div>
    );
  }
}
