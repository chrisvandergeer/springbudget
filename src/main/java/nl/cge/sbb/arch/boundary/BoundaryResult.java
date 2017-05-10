package nl.cge.sbb.arch.boundary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 08-05-17.
 */
public class BoundaryResult<E> {

    enum Result { SUCCESS, ERROR }

        private Result result;
        private String message;
        private E data;

        private BoundaryResult() {
            super();
        }

        public static BoundaryResult<Void> success() {
            return success(Void.class);
        }

        public static <T> BoundaryResult<List<T>> successWithListResult(Class<T> type) {
            return success(new ArrayList<T>().getClass());
        }

        public static <T> BoundaryResult success(Class<T> clazz) {
            BoundaryResult<T> boundaryResult = new BoundaryResult<T>();
            boundaryResult.result = Result.SUCCESS;
            return boundaryResult;
        }

        public static BoundaryResult<Void> error() {
            return error(Void.class);
        }

        public static <T> BoundaryResult error(Class<T> clazz) {
            BoundaryResult<T> boundaryResult = new BoundaryResult<T>();
            boundaryResult.result = Result.ERROR;
            return boundaryResult;
        }

        public Result getResult() {
            return result;
        }

        public String getMessage() {
            return message;
        }

        public BoundaryResult<E> setData(E data) {
            this.data = data;
            return this;
        }

        public E getData() {
            return data;
        }

        public BoundaryResult<E> setMessage(String message) {
            this.message = message;
            return this;
        }


}
