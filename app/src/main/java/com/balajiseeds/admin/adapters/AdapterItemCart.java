package com.balajiseeds.admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.balajiseeds.R;
import com.balajiseeds.employee.adapters.AdapterProducts;
import com.balajiseeds.employee.models.ModelCart;
import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.balajiseeds.employee.models.removeQunatity.RemoveQunatityJson;
import com.balajiseeds.utils.WebServices;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterItemCart extends RecyclerView.Adapter<AdapterItemCart.ViewHolder> {
    WebServices webServices;
    Context context;
    List<GetCartDetails> cartList;
    onQtyChange onQtyChange;

    public AdapterItemCart(Context context, List<GetCartDetails> cartList) {
        this.context = context;
        this.cartList = cartList;
        setOnchanged();
    }

    private void setOnchanged() {
        webServices=new WebServices(context);
        onQtyChange = new onQtyChange() {
            @Override
            public void QtyChanged(ViewHolder holder) {
                if (holder.qty == 0) {
                   /* webServices.removeFromCart(new ModelCart.deleteFromCart(cartList.get(holder.getAdapterPosition()).getProductId(), ""), new WebServices.onResponse() {
                        @Override
                        public void response() {
                            cartList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                        }
                    });*/

                } else {
                  /*  webServices.addToCart(new ModelCart.addtoCart(cartList.get(holder.getAdapterPosition()).getProductId(), ""+holder.qty), new WebServices.onResponse() {
                        @Override
                        public void response() {

                        }
                    });*/


                }
                holder.tv_qty.setText("" + holder.qty);

            }
        };

    }

    @NonNull
    @Override
    public AdapterItemCart.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemCart.ViewHolder holder, int position) {
        GetCartDetails cart=cartList.get(position);
        holder.tv_name.setText(cart.getProductName());
        holder.product_price.setText("Amount : "+cart.getTotal_amount());
        holder.tv_qty.setText(""+cart.getQty());
        holder.qty=Integer.valueOf(cart.getQty());
        holder.tv_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.qty++;
                onQtyChange.QtyChanged(holder);

                webServices.addQunatity(new RemoveQunatityJson( cart.getCartId() ), new WebServices.onResponse() {
                    @Override
                    public void response() {
                       /* holder.ll_count.setVisibility(View.GONE);
                        holder.tv_add.setVisibility(View.VISIBLE);*/
                    }
                });
            }
        });

        holder.product_varity.setText(cart.getProductVariety());

        holder.product_size.setText(cart.getPackagingName());
        holder.tv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.qty > 0) {
                    holder.qty--;
                }
                onQtyChange.QtyChanged(holder);


               /* webServices.removeFromCart(new ModelCart.deleteFromCart(cartList.get(holder.getAdapterPosition()).getProductId(), cart.getPartyId()  ), new WebServices.onResponse() {
                    @Override
                    public void response() {
                        cartList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        holder.ll_count.setVisibility(View.GONE);
                        holder.tv_add.setVisibility(View.VISIBLE);
                    }
                });*/

                webServices.removeQunatity(new RemoveQunatityJson( cart.getCartId() ), new WebServices.onResponse() {
                    @Override
                    public void response() {
                       /* holder.ll_count.setVisibility(View.GONE);
                        holder.tv_add.setVisibility(View.VISIBLE);*/
                        if (holder.qty == 0){

                            cartList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());

                        }
                    }
                });

            }
        });

        Glide.with(context).load(cart.getProductImage()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.prodImg);

      /*  holder.product_remove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });*/
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  tv_add, tv_qty, tv_plus, tv_minus,tv_name, product_varity, product_price, product_size;
        ImageView prodImg;
        int qty = 0;
        LinearLayout ll_count;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            product_varity = itemView.findViewById(R.id.product_varity);
            product_price = itemView.findViewById(R.id.product_price);
            product_size = itemView.findViewById(R.id.product_size);
            prodImg = itemView.findViewById(R.id.imageView4);
            tv_qty = itemView.findViewById(R.id.tvQuantity);
            tv_plus = itemView.findViewById(R.id.btnPlus);
            tv_minus = itemView.findViewById(R.id.btnMinus);
            tv_name = itemView.findViewById(R.id.tv_product_name);
            tv_add = itemView.findViewById(R.id.tv_add);
            ll_count = itemView.findViewById(R.id.ll_count);

        }
    }
    public interface onQtyChange {
        public void QtyChanged(AdapterItemCart.ViewHolder holder);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }
}
